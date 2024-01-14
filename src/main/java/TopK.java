import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TopK {

    public static class TopKMapper
            extends Mapper<Text, DeckSummaryWritable, Text, DeckSummaryWritable> {

        private HashMap<String, TopKStructure<Long, DeckSummaryWritable>> topKMap = new HashMap<>();

        private String createHashMapKey(String key) {
            String[] keys = key.split("-");
            return String.join("_", Arrays.copyOfRange(keys, 1, keys.length)); //Deleting deckId
        }

        @Override
        public void map(Text key, DeckSummaryWritable value, Context context
        ) throws IOException, InterruptedException {
            if(value.totalUses >= 100){
                String keyGranularity = createHashMapKey(key.toString());
                if(!topKMap.containsKey(keyGranularity)){
                    topKMap.put(keyGranularity, new TopKStructure<>(10, Long::compare));
                }

                topKMap.get(keyGranularity).addDeck(value.totalWins, value.clone());
            }

        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            for(Map.Entry<String, TopKStructure<Long, DeckSummaryWritable>> entry : topKMap.entrySet()) {
                String compositeKey = entry.getKey();
                TopKStructure<Long, DeckSummaryWritable> topkStructure = entry.getValue();
                for (DeckSummaryWritable value : topkStructure.topK.values()) {
                    context.write(new Text(compositeKey), value);
                }
            }
        }
    }

    public static class TopKReducer
            extends Reducer<Text,DeckSummaryWritable,Text,DeckSummaryWritable> {

        private TopKStructure<Long, DeckSummaryWritable> topKStructure = new TopKStructure<>(10, Long::compare);
        @Override
        public void reduce(Text key, Iterable<DeckSummaryWritable> values,
                           Context context
        ) throws IOException, InterruptedException {

            for(DeckSummaryWritable value : values){
                DeckSummaryWritable deck = value.clone();
                topKStructure.addDeck(deck.totalWins, deck);
            }

            for(DeckSummaryWritable value : topKStructure.getTopK().values())
                context.write(new Text(value.deckId), value);

            topKStructure.clearTreeMap();
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "TopK");
        job.setNumReduceTasks(1);
        job.setJarByClass(TopK.class);
        job.setMapperClass(TopK.TopKMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DeckSummaryWritable.class);
        job.setReducerClass(TopK.TopKReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DeckSummaryWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setInputFormatClass(SequenceFileInputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
