import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.HashSet;
import java.util.Locale;

public class DeckUniquePlayer {

    public static class DeckUniquePlayerMapper
            extends Mapper<Object, GameWritable, Text, UniquePlayerWritable> {
        private HashSet<UniquePlayerWritable> playerList = new HashSet<>();

        private void addInHasSet(Instant date, PlayerWritable player){
            LocalDateTime dateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());
            int year = dateTime.getYear();
            Month month = dateTime.getMonth();
            int week = dateTime.get(WeekFields.of(Locale.US).weekOfWeekBasedYear());
            String id_year = player.cards + "_" + year;
            String id_month = player.cards + "_" + month + "_" + year;
            String id_week = player.cards + "_" + week + "_" + year;
            playerList.add(new UniquePlayerWritable(id_year, player.playerId));
            playerList.add(new UniquePlayerWritable(id_month, player.playerId));
            playerList.add(new UniquePlayerWritable(id_week, player.playerId));
        }

        public void map(Object key, GameWritable value, Context context
        ) throws IOException, InterruptedException {
            GameWritable game = value.clone();
            addInHasSet(game.date, game.player1);
            addInHasSet(game.date, game.player2);
        }

        protected void cleanup(Context context) throws IOException, InterruptedException {
            for(UniquePlayerWritable player: playerList){
                context.write(new Text(player.deckId), player.clone());
            }
        }
    }
    public static class DeckUniquePlayerReducer
            extends Reducer<Text,UniquePlayerWritable,Text, LongWritable> {

        private HashSet<String> playerList = new HashSet<>();
        private LongWritable nbUniquePlayer = new LongWritable();

        public void reduce(Text key, Iterable<UniquePlayerWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            for(UniquePlayerWritable value: values)
                playerList.add(value.playerId);

            nbUniquePlayer.set(playerList.size());
            playerList.clear();
            context.write(key, nbUniquePlayer);
        }
    }
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "DeckUniquePlayer");
        job.setNumReduceTasks(2);
        job.setJarByClass(DeckUniquePlayer.class);
        job.setMapperClass(DeckUniquePlayer.DeckUniquePlayerMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(UniquePlayerWritable.class);
        job.setReducerClass(DeckUniquePlayer.DeckUniquePlayerReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        job.setInputFormatClass(SequenceFileInputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
