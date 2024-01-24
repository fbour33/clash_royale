import java.io.IOException;
import java.time.Instant;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class DataCleaning {
  public static class DataCleaningMapper
          extends Mapper<Object, Text, Text, GameWritable>{
    
    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException{

        JSONObject game = null;
        try {
            game = new JSONObject(value.toString());
            long clanTr1 = game.has("clanTr") ? game.getLong("clanTr") : -1; //Negative trophies don't exist
            long clanTr2 = game.has("clanTr2") ? game.getLong("clanTr2") : -1; //Negative trophies don't exist
            if(DataValidator.checkFields(game) && DataValidator.checkData(game) ) {
                PlayerWritable player1 = new PlayerWritable(game.getString("player"),
                        game.getLong("level"),
                        game.getDouble("deck"),
                        game.getDouble("all_deck"),
                        game.getString("clan"),
                        game.getLong("crown"),
                        game.getLong("exp"),
                        game.getLong("expPoints"),
                        DataValidator.sortCards(game.getString("cards")),
                        game.getLong("cardScore"),
                        clanTr1
                );
                PlayerWritable player2 = new PlayerWritable(game.getString("player2"),
                        game.getLong("level2"),
                        game.getDouble("deck2"),
                        game.getDouble("all_deck2"),
                        game.getString("clan2"),
                        game.getLong("crown2"),
                        game.getLong("exp2"),
                        game.getLong("expPoints2"),
                        DataValidator.sortCards(game.getString("cards2")),
                        game.getLong("cardScore2"),
                        clanTr2
                );


                GameWritable gameWritable = new GameWritable(Instant.parse(game.getString("date")),
                        game.getLong("round"), game.getString("type"), game.getString("mode"),
                        game.getLong("win"), player1, player2);



            String mapId = DataValidator.sortedUniqueId(game);
            context.write(new Text(mapId), gameWritable);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
  }
  public static class DataCleaningReducer
          extends Reducer<Text,GameWritable,Text,GameWritable> {
    public void reduce(Text key, Iterable<GameWritable> values,
                       Context context
    ) throws IOException, InterruptedException {
      context.write(key, values.iterator().next());
    }
  }
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "DataCleaning");
    job.setNumReduceTasks(3);
    job.setJarByClass(DataCleaning.class);
    job.setMapperClass(DataCleaningMapper.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(GameWritable.class);
    job.setReducerClass(DataCleaningReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(GameWritable.class);
    job.setOutputFormatClass(SequenceFileOutputFormat.class);
    job.setInputFormatClass(TextInputFormat.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
