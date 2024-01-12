import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;

public class DataStats {
  public static class DataStatsMapper
          extends Mapper<Object, GameWritable, Text, SortingDeckWritable>{

      private void writeSortingDeckByDate(Context context, PlayerWritable player, int win, double deckDiff, Instant date) throws IOException, InterruptedException {
          LocalDateTime dateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());
          int year = dateTime.getYear();
          Month month = dateTime.getMonth();
          int week = dateTime.get(WeekFields.of(Locale.US).weekOfWeekBasedYear());
          context.write(new Text(player.cards),
                  new SortingDeckWritable(player.cards, player.playerId, win, deckDiff, player.clanTr));
          Text id_year = new Text(player.cards + "_" + year);
          Text id_month = new Text(player.cards + "_" + month + "_" + year);
          Text id_week = new Text(player.cards + "_" + week + "_" + year);
          context.write(id_year,
                  new SortingDeckWritable(player.cards, player.playerId, win, deckDiff, player.clanTr));
          context.write(id_month,
                  new SortingDeckWritable(player.cards, player.playerId, win, deckDiff, player.clanTr));
          context.write(id_week,
                  new SortingDeckWritable(player.cards, player.playerId, win, deckDiff, player.clanTr));
      }

    private void writeSortingDeck(Context context, PlayerWritable player, int win, double deckDiff)
          throws IOException, InterruptedException {
      context.write(new Text(player.cards),
              new SortingDeckWritable(player.cards, player.playerId, win, deckDiff, player.clanTr));
    }

    public void map(Object key, GameWritable value, Context context
    ) throws IOException, InterruptedException {
        GameWritable game = value.clone();
        writeSortingDeck(context, game.player1, game.win == 1 ? 1 : 0, game.win == 1 ?
                game.player1.deck - game.player2.deck : 0);
        writeSortingDeck(context, game.player2, game.win == 0 ? 1 : 0,
                game.win == 0 ? game.player2.deck - game.player1.deck : 0);
    }
  }
  public static class DataStatsReducer
          extends Reducer<Text,SortingDeckWritable,Text,DeckSummaryWritable> {

    private HashMap<String, Integer> uniquePlayers = new HashMap<>();

    public void reduce(Text key, Iterable<SortingDeckWritable> values,
                       Context context
    ) throws IOException, InterruptedException {
        SortingDeckWritable sortedDeck = values.iterator().next().clone();
        long totalUses = 0;
        long totalWins = 0;
        long highestClanLevel = 0;
        double sumDeckStrength = 0;
        while(values.iterator().hasNext()) {
            uniquePlayers.put(sortedDeck.playerId, 1);
            totalUses += 1;
            totalWins += sortedDeck.win;
            if(sortedDeck.win == 1 && sortedDeck.clanLevel > highestClanLevel)
                highestClanLevel = sortedDeck.clanLevel;
            if(sortedDeck.win == 1)
                sumDeckStrength += sortedDeck.deckStrength;
        }
        DeckSummaryWritable deckSummary = new DeckSummaryWritable(sortedDeck.deckId, totalWins,
                totalUses, uniquePlayers.size(), highestClanLevel,
                sumDeckStrength/totalWins);
        uniquePlayers.clear();
        context.write(key, deckSummary);
    }
  }
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "DataStats");
    job.setNumReduceTasks(1);
    job.setJarByClass(DataStats.class);
    job.setMapperClass(DataStatsMapper.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(SortingDeckWritable.class);
    job.setReducerClass(DataStatsReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(DeckSummaryWritable.class);
    job.setOutputFormatClass(SequenceFileOutputFormat.class);
    job.setInputFormatClass(SequenceFileInputFormat.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
