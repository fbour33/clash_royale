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

    public String checkCards(String cards){
      if(cards.length() == 18 && cards.endsWith("6E"))
        return cards.substring(0,17);
      else if(cards.length() > 16)
        throw new IllegalArgumentException("Cards invalid.");
      return cards;
    }

    public boolean checkInput(GameWritable game){
      // Tester si les données entrées sont valides
      return true;
    }

    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {

      try {
        GameWritable game = gson.fromJson(value.toString(), GameWritable.class);

        String[] sorted = Stream.of(game.player1, game.player2).sorted().toArray(String[]::new);

        game.cards1 = checkCards(game.cards1);
        game.cards2 = checkCards(game.cards2);

        if (!(game.cards1.isEmpty() && game.cards2.isEmpty()) && game.deck1 != 0 && game.deck2 != 0)
          context.write(new Text(game.date.toString() + "_" + game.round + "_" + sorted[0] + "_" + sorted[1]), game);
      }catch (IOException e){
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
