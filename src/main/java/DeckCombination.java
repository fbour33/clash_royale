import java.util.ArrayList;
import java.util.List;

public class DeckCombination {

    private static void generateCombinationsRecursive(List<String> allCards, List<String> allCombinations, String currentCombination, int currentIndex, int combinationSize) {
        if (currentCombination.length() == combinationSize * 2) {
            allCombinations.add(currentCombination);
            return;
        }

        for (int i = currentIndex; i < allCards.size(); i++) {
            String newCombination = currentCombination + allCards.get(i);
            generateCombinationsRecursive(allCards, allCombinations, newCombination, i + 1, combinationSize);
        }
    }

    public static List<String> generateAllCombinations(String cardsStr) {
        List<String> allCombinations = new ArrayList<>();
        List<String> cards = new ArrayList<>();

        for (int i = 0; i < cardsStr.length(); i += 2) {
            cards.add(cardsStr.substring(i, Math.min(i + 2, cardsStr.length())));
        }

        for (int size = 1; size <= cards.size(); size++) {
            generateCombinationsRecursive(cards, allCombinations, "", 0, size);
        }
        return allCombinations;
    }

    public static void main(String[] args) {
        String cardsStr = "9a3e6c";

        List<String> combinations = generateAllCombinations(cardsStr);
        for (String combination : combinations) {
            System.out.print(combination + " ; ") ;
        }
    }
}
