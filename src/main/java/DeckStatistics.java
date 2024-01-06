import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class DeckStatistics {

    public static class DeckStatisticsMapper extends Mapper<Object, Text, Text, StatisticsWritable> {

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            // TODO
        }
    }

    public static class DeckStatisticsReducer extends Reducer<Text, StatisticsWritable, Text, StatisticsWritable> {

        public void reduce(Text key, Iterable<StatisticsWritable> values, Context context
        ) throws IOException, InterruptedException {
           //TODO
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "DeckStatistics");
        job.setNumReduceTasks(1);
        job.setJarByClass(DeckStatistics.class);
        job.setMapperClass(DeckStatisticsMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(DeckStatisticsReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
