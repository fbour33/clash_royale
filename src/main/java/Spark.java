import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;

public class Spark {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("ClashRoyaleSparkNgrams");
        JavaSparkContext context = new JavaSparkContext(conf);

        // Chargement des données
        JavaPairRDD<Text, DeckSummaryWritable> gameDataRDD = context.sequenceFile(args[0], Text.class, DeckSummaryWritable.class);

        /*
        // Récupérer les données par semaine
        JavaPairRDD<Text, DeckSummaryWritable> filteredDataRDD = gameDataRDD.filter(
                //Filtrer résultat par semaine
                game -> ()
        );

        // Calcul statistiques pour chaque combinaison
        JavaPairRDD<Text, DeckSummaryWritable> ngramsRDD = gameDataRDD.mapToPair(
                game -> {
                    List<String> ngrams = DeckCombinaison.generateAllCombinations(game._2.deckId);
                }
        );
        */

        // Sauvegarde des données

        context.close();
    }
}
