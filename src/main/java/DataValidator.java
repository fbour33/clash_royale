import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class DataValidator {
    private final static ArrayList<String> keys = new ArrayList<String>(Arrays.asList(
            "date", "round", "type", "mode", "player", "level", "all_deck",
            "touch", "touch2", "deck", "clan", "crown", "elixir", "player2", "level2",
            "all_deck2", "deck2", "clan2", "crown2", "elixir2", "win", "cards", "cards2", "exp",
            "expPoints", "cardScore", "exp2", "expPoints2", "cardScore2"
    ));
    public static boolean checkFields(JSONObject game) {
        for(String key : keys)
            if (!game.has(key))
                return false;
        return true;
    }

    public static boolean checkData(JSONObject game) throws JSONException {
        if(game.getString("cards").length() > 18 && !game.getString("cards").endsWith("6e"))
            return false;
        if(game.getString("cards2").length() > 18 && !game.getString("cards2").endsWith("6e")) //To keep new meta with evo
            return false;
        if(game.getDouble("deck") == 0 && game.getDouble("deck2") == 0)
            return false;
        if(game.getString("player").isEmpty() || game.getString("player2").isEmpty())
            return false;
        if(game.getLong("touch") == 0 || game.getLong("touch2") == 0)
            return false;
        return true;
    }

    public static String sortedUniqueId(JSONObject game) throws JSONException {
        String[] sorted = Stream.of(game.getString("player"), game.getString("player2")).sorted().toArray(String[]::new);
        return game.getString("date") + "_" + game.getLong("round") + "_" + sorted[0] + "_" + sorted[1];
    }

    public static String sortCards(String cards){
        if(cards.length() == 18 && cards.endsWith("6e"))
            cards = cards.substring(0,cards.length()-2);

        List<String> hexPairs = new ArrayList<>();
        String firstPair = cards.substring(0, 2);
        for (int i = 2; i < cards.length(); i += 2)
            hexPairs.add(cards.substring(i, i + 2));
        Collections.sort(hexPairs);

        return firstPair + String.join("", hexPairs);
    }


}
