import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardCombinations {
//    public static void main(String[] args) {
//        String[] cards = {"01", "23", "45", "67", "89", "ab", "cd", "ef"}; // Vos cartes
//        List<String> allCombinations = getTwoAndThreeCombinations("01cd2389ab4567ef");
//
//        for (String combination : allCombinations) {
//            System.out.println(combination);
//        }
//
//        System.out.println(Arrays.toString(divideDeckIntoPairs("01cd2389ab4567ef")));
//
//    }

    /* Divide cards (deckId) into pairs and sort result array */
    private static String[] divideDeckIntoPairs(String deckId){
        ArrayList<String> hexPairs = new ArrayList<>();
        for(int i = 0; i < deckId.length(); i += 2){
            hexPairs.add(deckId.substring(i, i + 2));
        }
        Collections.sort(hexPairs);
        return hexPairs.toArray(new String[0]);
    }

    public static List<String> getAllCombinations(String deckId) {
        String[] cards =  divideDeckIntoPairs(deckId);
        List<String> result = new ArrayList<>();

        int total = 1 << cards.length;

        for (int i = 0; i < total; i++) {
            StringBuilder combination = new StringBuilder();
            for (int j = 0; j < cards.length; j++) {

                if (((i >> j) & 1) != 0) {
                    combination.append(cards[j]);
                }
            }
            result.add(combination.toString().trim());
        }

        return result;
    }

    public static List<String> getTwoAndThreeCombinations(String deckId) {
        String[] cards =  divideDeckIntoPairs(deckId);
        List<String> combinations = new ArrayList<>();
        combine(cards, 0, 2, "", combinations);
        combine(cards, 0, 3, "", combinations);
        return combinations;
    }

    private static void combine(String[] cards, int start, int combinationSize, String current, List<String> combinations) {
        if (combinationSize == 0) {
            combinations.add(current.trim());
            return;
        }

        for (int i = start; i <= cards.length - combinationSize; i++) {
            combine(cards, i + 1, combinationSize - 1, current + cards[i], combinations);
        }
    }
}
