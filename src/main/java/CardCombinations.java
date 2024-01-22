import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardCombinations {
//    public static void main(String[] args) {
//        String[] cards = {"01", "23", "45", "67", "89", "ab", "cd", "ef"}; // Vos cartes
//        List<String> allCombinations = getAllCombinations(cards);
//
//        for (String combination : allCombinations) {
//            System.out.println(combination);
//        }
//
//        System.out.println(Arrays.toString(divideDeckIntoPairs("01cd2389ab4567ef")));
//
//    }

//    public static String[] divideDeckIntoPairs(String deckId){
//        ArrayList<String> hexPairs = new ArrayList<>();
//        for(int i = 0; i < 16; i += 2){
//            hexPairs.add(deckId.substring(i, i + 2));
//        }
//        Collections.sort(hexPairs);
//        return hexPairs.toArray(new String[0]);
//    }

    public static List<String> getAllCombinations(String[] cards) {
        List<String> result = new ArrayList<>();

        // Le nombre total de combinaisons est 2^n (où n est le nombre de cartes)
        int total = 1 << cards.length;

        // Générer chaque combinaison possible
        for (int i = 0; i < total; i++) {
            StringBuilder combination = new StringBuilder();
            for (int j = 0; j < cards.length; j++) {
                // (i >> j) & 1) vérifie si le j-ème bit de i est défini
                if (((i >> j) & 1) != 0) {
                    combination.append(cards[j]);
                }
            }
            result.add(combination.toString().trim());
        }

        return result;
    }
}
