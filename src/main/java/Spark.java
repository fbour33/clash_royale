import com.google.gson.Gson;
import org.apache.spark.HashPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.hadoop.io.Text;
import scala.Tuple2;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;

public class Spark {

    /* Concatenate key with date granularity */
    private static String keyWithGranularity(String card, Instant date){
        LocalDateTime dateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());
        int year = dateTime.getYear();
        int week = dateTime.get(WeekFields.of(Locale.US).weekOfWeekBasedYear());
        return card + "_" + week + "_" + year;
    }


    /* Get all the card combinations */
    private static List<String> createKeys(String deckId){
        return CardCombinations.getTwoAndThreeCombinations(deckId);
        //return CardCombinations.getAllCombinations(deckId);
    }

    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setAppName("TP Spark")
                .set("spark.task.maxFailures", "1")
                .set("spark.executor.retries", "0"); //Remove retry when job crashed
        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<GameWritable> gameData = context.sequenceFile(args[0], Text.class, GameWritable.class).values();

//        int nbPartitions = gameData.getNumPartitions();
//        System.out.println("Nb partitions : " + nbPartitions);

        /* Changing GameWritable into DeckSummaryWritable with uniquePlayer initiate at 0.
           uniquePlayer is calculated in another RDD which will join this RDD below */
        JavaPairRDD<String, DeckSummaryWritable> deckSummaries = gameData.flatMapToPair(
            (game) -> {
                List<Tuple2<String, DeckSummaryWritable>> deckList = new ArrayList<>();
                List<String> keyPlayer1 = createKeys(game.player1.cards);
                List<String> keyPlayer2 = createKeys(game.player2.cards);
                String key = "";
                for (int i = 0; i < keyPlayer1.size(); i++) { // keyPlayer1.size() == keyPlayer2.size()
                    key = keyWithGranularity(keyPlayer1.get(i), game.date);
                    Tuple2<String, DeckSummaryWritable> deckSummaryPlayer1 = new Tuple2<>(key,
                            new DeckSummaryWritable(key, game.win == 1 ? 1 : 0,
                                    1, 0, game.player1.clanTr,
                                    game.win == 1 ? game.player1.deck - game.player2.deck : 0, 0));
                    deckList.add(deckSummaryPlayer1);
                    key = keyWithGranularity(keyPlayer2.get(i), game.date);
                    Tuple2<String, DeckSummaryWritable> deckSummaryPlayer2 = new Tuple2<>(key,
                            new DeckSummaryWritable(key, game.win == 0 ? 1 : 0,
                                    1, 0, game.player2.clanTr,
                                    game.win == 0 ? game.player2.deck - game.player1.deck : 0, 0));
                    deckList.add(deckSummaryPlayer2);
                }
                return deckList.iterator();
            }
        ).reduceByKey(
                (DeckSummaryWritable a, DeckSummaryWritable b) -> {
                    long totalWins = a.totalWins + b.totalWins;
                    long totalUses = a.totalUses + b.totalUses;
                    long highestClanLevel = Math.max(a.highestClanLevel, b.highestClanLevel);

                    double sumDeckStrength = 0;
                    if (a.totalWins == 1) sumDeckStrength += a.avgDeckStrength;
                    if (b.totalWins == 1) sumDeckStrength += b.avgDeckStrength;

                    return new DeckSummaryWritable(a.deckId, totalWins, totalUses, 0, highestClanLevel, sumDeckStrength, 0);
                }
        );


        JavaPairRDD<String, String> uniquePlayers = gameData.flatMapToPair(
            (game) -> {
                List<Tuple2<String, String>> deckList = new LinkedList<>();
                List<String> keyPlayer1 = createKeys(game.player1.cards);
                List<String> keyPlayer2 = createKeys(game.player2.cards);
                for (int i = 0; i < keyPlayer1.size(); i++) {
                    Tuple2<String, String> uniquePlayer1 = new Tuple2<>(keyWithGranularity(keyPlayer1.get(i), game.date), game.player1.playerId);
                    Tuple2<String, String> uniquePlayer2 = new Tuple2<>(keyWithGranularity(keyPlayer2.get(i), game.date), game.player2.playerId);
                    deckList.add(uniquePlayer1);
                    deckList.add(uniquePlayer2);
                }
                return deckList.iterator();
            }
        );

        JavaPairRDD<String, Long> uniquePlayerCount = uniquePlayers
                .distinct().
                mapToPair(x -> new Tuple2<>(x._1, 1L))
                .reduceByKey(Long::sum);

//        JavaPairRDD<String, DeckSummaryWritable> partitionedDeckSummaries = deckSummaries.partitionBy(new HashPartitioner(2200));
//        JavaPairRDD<String, Long> partitionedUniquePlayers = uniquePlayerCount.partitionBy(new HashPartitioner(2200));
//
//        JavaPairRDD<String, Tuple2<DeckSummaryWritable, Long>> joinedRDD = partitionedDeckSummaries.join(partitionedUniquePlayers);

        JavaPairRDD<String, Tuple2<DeckSummaryWritable, Long>> joinedRDD = deckSummaries.join(uniquePlayerCount);

        JavaPairRDD<String, DeckSummaryWritable> updatedDeckSummaries = joinedRDD.mapToPair(
                tuple -> {
                    String key = tuple._1();
                    DeckSummaryWritable deckSummary = tuple._2()._1();

                    deckSummary.uniquePlayers = tuple._2()._2();
                    if(deckSummary.totalUses != 0)
                        deckSummary.avgDeckStrength = deckSummary.avgDeckStrength / deckSummary.totalUses;
                    if(deckSummary.totalWins != 0)
                        deckSummary.winRate = (double) deckSummary.totalWins / deckSummary.totalUses;

                    return new Tuple2<>(key, deckSummary);
                }
        );

        JavaPairRDD<String, Tuple2<String, Double>> deckWithDateWinRate = updatedDeckSummaries.mapToPair(
                (deck) -> {
                    String[] keyParts = deck._1.split("_");
                    return new Tuple2<>(keyParts[0], new Tuple2<>(keyParts[1] + "_" + keyParts[2], deck._2.winRate));
                }
        );

        JavaPairRDD<String, Iterable<Tuple2<String, Double>>> groupedDeckWithDateWinRate = deckWithDateWinRate.groupByKey();

        JavaPairRDD<String, List<Tuple2<String, Double>>> NGramCardCombination = groupedDeckWithDateWinRate
            .mapValues(iterable -> {
                List<Tuple2<String, Double>> sortedList = new ArrayList<>();
                iterable.forEach(sortedList::add);
                sortedList.sort(Comparator.comparing(Tuple2::_1));
                return sortedList;
            });

        NGramCardCombination.map(pair -> new Gson().toJson(pair)).saveAsTextFile(args[1]);

    }

}
