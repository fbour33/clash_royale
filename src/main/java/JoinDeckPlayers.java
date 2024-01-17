import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class JoinDeckPlayers {

    public static class JoinMapperDeck
        extends Mapper<Text, DeckSummaryWritable, Text, DeckSummaryWritable> {

            @Override
            public void map(Text key, DeckSummaryWritable value, Context context
            ) throws IOException, InterruptedException {
                DeckSummaryWritable deck = value.clone();
                if(deck.totalWins != 0)
                    deck.avgDeckStrength = deck.avgDeckStrength/ deck.totalWins;
                context.write(key, deck);
            }

        }

        public static class JoinMapperPlayer
            extends Mapper<Text, LongWritable, Text, DeckSummaryWritable> {

        @Override
        public void map(Text key, LongWritable value, Context context
        ) throws IOException, InterruptedException {

            context.write(key, new DeckSummaryWritable(key.toString(), 0L, 0L, value.get(), 0L, 0D));
        }
    }

    public static class JoinReducer
            extends Reducer<Text,DeckSummaryWritable,Text,DeckSummaryWritable> {

        public void reduce(Text key, Iterable<DeckSummaryWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            DeckSummaryWritable uniquePlayer = null;
            DeckSummaryWritable deckWithoutPlayer = null;
            for(DeckSummaryWritable value: values) {
                if (value.uniquePlayers != 0L)
                    uniquePlayer = value.clone();
                else
                    deckWithoutPlayer = value.clone();
            }
            if(uniquePlayer != null && deckWithoutPlayer != null){
                deckWithoutPlayer.uniquePlayers = uniquePlayer.uniquePlayers;
                context.write(key, deckWithoutPlayer);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "JoinDeckPlayers");
        job.setNumReduceTasks(1);
        job.setJarByClass(JoinDeckPlayers.class);
        MultipleInputs.addInputPath(job, new Path(args[0]), SequenceFileInputFormat.class, JoinMapperDeck.class);
        MultipleInputs.addInputPath(job, new Path(args[1]), SequenceFileInputFormat.class, JoinMapperPlayer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DeckSummaryWritable.class);
        job.setReducerClass(JoinDeckPlayers.JoinReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DeckSummaryWritable.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
