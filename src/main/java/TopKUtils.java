import java.util.Comparator;
import java.util.function.Function;

public class TopKUtils {

    public static Function<DeckSummaryWritable, ?> getKey(String key){
        if (key.equals("highestClanLevel"))
            return deck -> deck.highestClanLevel;
        else if(key.equals("avgDeckStrength"))
            return deck -> deck.avgDeckStrength;
        return deck -> deck.totalWins;
    }

    public static Comparator<?> getComparator(String key){
        if(key.equals("avgDeckStrength"))
            return Comparator.comparingDouble((Double x) -> x).reversed();
        return Comparator.comparingLong((Long x) -> x);
    }
}
