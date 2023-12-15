import java.io.IOException;
import java.util.stream.Stream;

import com.google.gson.Gson;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class DataCleaning {
  public static class DataCleaningMapper
          extends Mapper<Object, Text, Text, GameWritable>{
    Gson gson = new Gson();

    public String cardLength(String cards){
      if(cards.length() == 18 && cards.endsWith("6e"))
        return cards.substring(0,cards.length()-2);
      else if(cards.length() > 16)
        return "";
      return cards;
    }
    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {
      GameWritable game = gson.fromJson(value.toString(), GameWritable.class);
      String[] sorted = Stream.of(game.player1, game.player2).sorted().toArray(String[]::new);

      game.cards1 = cardLength(game.cards1);
      game.cards2 = cardLength(game.cards2);

      if(!(game.cards1.isEmpty() && game.cards2.isEmpty()) && game.deck1 !=0  && game.deck2 !=0)
        context.write(new Text(game.date.toString() + "_" + game.round + "_" + sorted[0] + "_" + sorted[1]), game);
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
    job.setNumReduceTasks(1);
    job.setJarByClass(DataCleaning.class);
    job.setMapperClass(DataCleaningMapper.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    job.setReducerClass(DataCleaningReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    job.setOutputFormatClass(TextOutputFormat.class);
    job.setInputFormatClass(TextInputFormat.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
