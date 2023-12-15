import com.fasterxml.jackson.annotation.JsonAlias;
import org.apache.hadoop.io.Writable;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class GameWritable implements Writable {

    @JsonProperty("date")
    Instant date;

    @JsonProperty("round")
    int round;
    @JsonProperty("type")
    String type;
    @JsonProperty("mode")
    String mode;

    /* Player 1 */

    @JsonProperty("player")
    String player1;
    @JsonProperty("level")
    long level1;

    @JsonProperty("all_deck")
    double allDeck1;
    @JsonProperty("deck")
    double deck1;
    @JsonProperty("clan")
    String clan1;
    @JsonProperty("crown")
    int crown1;
    @JsonProperty("touch")
    boolean touch1;
    @JsonProperty("elixir")
    double elixir1;
    @JsonProperty("exp")
    long exp1;
    @JsonProperty("expPoints")
    long expPoints1;
    @JsonProperty("cardScore")
    long cardScore1;
    @JsonProperty("clanTr")
    long clanTr1;

    /* Player 2 */

    @JsonProperty("player2")
    String player2;
    @JsonProperty("level2")
    long level2;

    @JsonProperty("all_deck2")
    double allDeck2;
    @JsonProperty("deck2")
    double deck2;
    @JsonProperty("clan2")
    String clan2;
    @JsonProperty("crown2")
    int crown2;
    @JsonProperty("touch2")
    boolean touch2;
    @JsonProperty("elixir2")
    double elixir2;
    @JsonProperty("exp2")
    long exp2;
    @JsonProperty("expPoints2")
    long expPoints2;
    @JsonProperty("cardScore2")
    long cardScore2;
    @JsonProperty("clanTr2")
    long clanTr2;


    public GameWritable(Instant date, int round, String type, String mode,
                       String player1, long level1, double allDeck1, double deck1,
                       String clan1, int crown1, boolean touch1, double elixir1,
                       long exp1, long expPoints1, long cardScore1, long clanTr1,
                       String player2, long level2, double allDeck2, double deck2,
                       String clan2, int crown2, boolean touch2, double elixir2,
                       long exp2, long expPoints2, long cardScore2, long clanTr2) {
        this.date = date;
        this.round = round;
        this.type = type;
        this.mode = mode;
        this.player1 = player1;
        this.level1 = level1;
        this.allDeck1 = allDeck1;
        this.deck1 = deck1;
        this.clan1 = clan1;
        this.crown1 = crown1;
        this.touch1 = touch1;
        this.elixir1 = elixir1;
        this.exp1 = exp1;
        this.expPoints1 = expPoints1;
        this.cardScore1 = cardScore1;
        this.clanTr1 = clanTr1;
        this.player2 = player2;
        this.level2 = level2;
        this.allDeck2 = allDeck2;
        this.deck2 = deck2;
        this.clan2 = clan2;
        this.crown2 = crown2;
        this.touch2 = touch2;
        this.elixir2 = elixir2;
        this.exp2 = exp2;
        this.expPoints2 = expPoints2;
        this.cardScore2 = cardScore2;
        this.clanTr2 = clanTr2;
    }

    public void write(DataOutput out) throws IOException {
        out.writeLong(date.toEpochMilli());
        out.writeInt(round);
        out.write(type.getBytes());
        out.write(mode.getBytes());
        out.write(player1.getBytes());
        out.writeLong(level1);
        out.writeDouble(allDeck1);
        out.writeDouble(deck1);
        out.write(clan1.getBytes());
        out.writeInt(crown1);
        out.writeBoolean(touch1);
        out.writeDouble(elixir1);
        out.writeLong(exp1);
        out.writeLong(expPoints1);
        out.writeLong(cardScore1);
        out.writeLong(clanTr1);
        out.write(player2.getBytes());
        out.writeLong(level2);
        out.writeDouble(allDeck2);
        out.writeDouble(deck2);
        out.write(clan2.getBytes());
        out.writeInt(crown2);
        out.writeBoolean(touch2);
        out.writeDouble(elixir2);
        out.writeLong(exp2);
        out.writeLong(expPoints2);
        out.writeLong(cardScore2);
        out.writeLong(clanTr2);
    }






}
