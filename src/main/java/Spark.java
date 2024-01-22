import com.google.gson.Gson;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.hadoop.io.Text;
import scala.Tuple2;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;

public class Spark {

    private static String[] divideDeckIntoPairs(String deckId){
        ArrayList<String> hexPairs = new ArrayList<>();
        for(int i = 0; i < deckId.length(); i += 2){
            hexPairs.add(deckId.substring(i, i + 2));
        }
        Collections.sort(hexPairs);
        return hexPairs.toArray(new String[0]);
    }

    private static String combineCardsWithGranularity(String cards, String granularity, Instant date){
        LocalDateTime dateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());
        int year = dateTime.getYear();
        Month month = dateTime.getMonth();
        int week = dateTime.get(WeekFields.of(Locale.US).weekOfWeekBasedYear());
        StringBuilder sb = new StringBuilder();
        if(granularity.equals("month"))
            sb.append(cards).append("_").append(month).append("_").append(year);
        else if(granularity.equals("week"))
            sb.append(cards).append("_").append(week).append("_").append(year);
        else
            sb.append(cards).append("_").append(year);
        return sb.toString();
    }
    private static ArrayList<String> combinationWithDateGranularity(List<String> cardCombination, Instant date){
        LocalDateTime dateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());
        int year = dateTime.getYear();
        Month month = dateTime.getMonth();
        int week = dateTime.get(WeekFields.of(Locale.US).weekOfWeekBasedYear());
        ArrayList<String> keyWithGranularity = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(String combination: cardCombination){
            sb.append(combination).append("_").append(week).append("_").append(year);
            keyWithGranularity.add(sb.toString());
            sb.setLength(0);
            sb.append(combination).append("_").append(month).append("_").append(year);
            keyWithGranularity.add(sb.toString());
        }
        return keyWithGranularity;
    }

    private static ArrayList<String> createKeys(String deckId, Instant date){
        String[] hexPairs =  divideDeckIntoPairs(deckId);
        List<String> cardCombination = CardCombinations.getAllCombinations(hexPairs);
        return combinationWithDateGranularity(cardCombination, date);
    }

    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setAppName("TP Spark");
        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<GameWritable> gameData = context.sequenceFile(args[0], Text.class, GameWritable.class).values();

        int nbPartitions = gameData.getNumPartitions();
        System.out.println("Nb partitions : " + nbPartitions); // default : 2

        //Change GameWritable into deckSummary
        JavaPairRDD<String, DeckSummaryWritable> deckSummaries = gameData.flatMapToPair(
            (game) -> {
                List<Tuple2<String, DeckSummaryWritable>> deckList = new ArrayList<>();
                ArrayList<String> keyPlayer1 = createKeys(game.player1.cards, game.date);
                ArrayList<String> keyPlayer2 = createKeys(game.player2.cards, game.date);
                if(keyPlayer2.size() != keyPlayer1.size())
                    System.out.println("KeyPlayer 1 :" + keyPlayer1.size() + ", KeyPlayer 2 : " + keyPlayer2.size());
                for (int i = 0; i < keyPlayer1.size(); i++) { // keyPlayer1.size() == keyPlayer2.size()
                    Tuple2<String, DeckSummaryWritable> deckSummaryPlayer1 = new Tuple2<>(keyPlayer1.get(i),
                            new DeckSummaryWritable(keyPlayer1.get(i), game.win == 1 ? 1 : 0,
                                    1, 0, game.player1.clanTr,
                                    game.win == 1 ? game.player1.deck - game.player2.deck : 0));
                    deckList.add(deckSummaryPlayer1);
                    Tuple2<String, DeckSummaryWritable> deckSummaryPlayer2 = new Tuple2<>(keyPlayer2.get(i),
                            new DeckSummaryWritable(keyPlayer2.get(i), game.win == 0 ? 1 : 0,
                                    1, 0, game.player2.clanTr,
                                    game.win == 0 ? game.player2.deck - game.player1.deck : 0));
                    deckList.add(deckSummaryPlayer2);
                }
                return deckList.iterator();
            }
        );

        deckSummaries = deckSummaries.reduceByKey(
                (DeckSummaryWritable a, DeckSummaryWritable b) -> {
                    long totalWins = a.totalWins + b.totalWins;
                    long totalUses = a.totalUses + b.totalUses;
                    long highestClanLevel = Math.max(a.highestClanLevel, b.highestClanLevel);

                    double sumDeckStrength = 0;
                    if (a.totalWins == 1) sumDeckStrength += a.avgDeckStrength;
                    if (b.totalWins == 1) sumDeckStrength += b.avgDeckStrength;

                    return new DeckSummaryWritable(a.deckId, totalWins, totalUses, 0, highestClanLevel, sumDeckStrength);
                }
        );

        JavaPairRDD<String, String> uniquePlayers = gameData.flatMapToPair(
            (game) -> {
                List<Tuple2<String, String>> deckList = new LinkedList<>();
                ArrayList<String> keyPlayer1 = createKeys(game.player1.cards, game.date);
                ArrayList<String> keyPlayer2 = createKeys(game.player2.cards, game.date);
                for (int i = 0; i < keyPlayer1.size(); i++) {
                    Tuple2<String, String> uniquePlayer1 = new Tuple2<>(keyPlayer1.get(i), game.player1.playerId);
                    Tuple2<String, String> uniquePlayer2 = new Tuple2<>(keyPlayer2.get(i), game.player2.playerId);
                    deckList.add(uniquePlayer1);
                    deckList.add(uniquePlayer2);
                }
                return deckList.iterator();
            }
        );

        JavaPairRDD<String, Long> uniquePlayerCount = uniquePlayers
                .distinct().
                mapToPair(x -> new Tuple2<String, Long>(x._1, 1L))
                .reduceByKey(Long::sum);

        JavaPairRDD<String, Tuple2<DeckSummaryWritable, Long>> joinedRDD = deckSummaries.join(uniquePlayerCount);

        JavaPairRDD<String, DeckSummaryWritable> updatedDeckSummaries = joinedRDD.mapToPair(
                tuple -> {
                    String key = tuple._1();
                    DeckSummaryWritable deckSummary = tuple._2()._1();
                    Long playerCount = tuple._2()._2();

                    deckSummary.uniquePlayers = playerCount;
                    deckSummary.avgDeckStrength = deckSummary.avgDeckStrength / deckSummary.totalUses;

                    return new Tuple2<>(key, deckSummary);
                }
        );

        updatedDeckSummaries
                .map(pair -> new Gson().toJson(pair))
                .saveAsTextFile(args[1]);

    }

}
