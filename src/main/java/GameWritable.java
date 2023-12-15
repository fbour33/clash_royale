import org.apache.hadoop.io.Writable;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.time.Instant;

public class GameWritable implements Writable {

    @JsonProperty("date")
    Instant date;

    @JsonProperty("round")
    long round;
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
    long crown1;
    @JsonProperty("touch")
    boolean touch1;
    @JsonProperty("elixir")
    double elixir1;
    @JsonProperty("exp")
    long exp1;
    @JsonProperty("expPoints")
    long expPoints1;
    @JsonProperty("cards")
    String cards1;
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
    long crown2;
    @JsonProperty("touch2")
    boolean touch2;
    @JsonProperty("elixir2")
    double elixir2;
    @JsonProperty("exp2")
    long exp2;
    @JsonProperty("expPoints2")
    long expPoints2;
    @JsonProperty("cards2")
    String cards2;
    @JsonProperty("cardScore2")
    long cardScore2;
    @JsonProperty("clanTr2")
    long clanTr2;


    public GameWritable(Instant date, long round, String type, String mode,
                       String player1, long level1, double allDeck1, double deck1,
                       String clan1, long crown1, boolean touch1, double elixir1,
                       long exp1, long expPoints1, String cards1, long cardScore1, long clanTr1,
                       String player2, long level2, double allDeck2, double deck2,
                       String clan2, long crown2, boolean touch2, double elixir2,
                       long exp2, long expPoints2, String cards2, long cardScore2, long clanTr2) {
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
        this.cards1 = cards1;
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
        this.cards2 = cards2;
        this.cardScore2 = cardScore2;
        this.clanTr2 = clanTr2;
    }

    public void write(DataOutput out) throws IOException {
        out.writeLong(date.toEpochMilli());
        out.writeLong(round);
        out.write(type.getBytes());
        out.write(mode.getBytes());
        out.write(player1.getBytes());
        out.writeLong(level1);
        out.writeDouble(allDeck1);
        out.writeDouble(deck1);
        out.write(clan1.getBytes());
        out.writeLong(crown1);
        out.writeBoolean(touch1);
        out.writeDouble(elixir1);
        out.writeLong(exp1);
        out.writeLong(expPoints1);
        out.write(cards1.getBytes());
        out.writeLong(cardScore1);
        out.writeLong(clanTr1);
        out.write(cards1.getBytes());
        out.write(player2.getBytes());
        out.writeLong(level2);
        out.writeDouble(allDeck2);
        out.writeDouble(deck2);
        out.write(clan2.getBytes());
        out.writeLong(crown2);
        out.writeBoolean(touch2);
        out.writeDouble(elixir2);
        out.writeLong(exp2);
        out.writeLong(expPoints2);
        out.write(cards2.getBytes());
        out.writeLong(cardScore2);
        out.writeLong(clanTr2);
        out.write(cards2.getBytes());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        date = Instant.ofEpochSecond(in.readLong());
        round= in.readLong();
        type= String.valueOf(in.readByte());
        mode = String.valueOf(in.readByte());
        player1= String.valueOf(in.readByte());
        level1= in.readLong();
        allDeck1= in.readDouble();
        deck1= in.readDouble();
        clan1= String.valueOf(in.readByte());
        crown1= in.readLong();
        touch1=in.readBoolean();
        elixir1=in.readDouble();
        exp1=in.readLong();
        expPoints1=in.readLong();
        cards1= String.valueOf(in.readByte());
        cardScore1=in.readLong();
        clanTr1=in.readLong();
        player2= String.valueOf(in.readByte());
        level2=in.readLong();
        allDeck2=in.readDouble();
        deck2=in.readDouble();
        clan2=String.valueOf(in.readByte());
        crown2=in.readLong();
        touch2=in.readBoolean();
        elixir2=in.readDouble();
        exp2=in.readLong();
        expPoints2=in.readLong();
        cards2= String.valueOf(in.readByte());
        cardScore2=in.readLong();
        clanTr2=in.readLong();
    }

    @Override
    public String toString() {
        return "{" +
                "\"date\":\"" + date.toString() + "\"," +
                "\"round\":" + round + "," +
                "\"type\":\"" + type + "\"," +
                "\"mode\":\"" + mode + "\"," +
                "\"player1\":\"" + player1 + "\"," +
                "\"level1\":" + level1 + "," +
                "\"all_deck1\":" + allDeck1 + "," +
                "\"deck1\":" + deck1 + "," +
                "\"clan1\":\"" + clan1 + "\"," +
                "\"crown1\":" + crown1 + "," +
                "\"touch1\":" + touch1 + "," +
                "\"elixir1\":" + elixir1 + "," +
                "\"exp1\":" + exp1 + "," +
                "\"expPoints1\":" + expPoints1 + "," +
                "\"cards1\":\"" + cards1 + "\"," +
                "\"cardScore1\":" + cardScore1 + "," +
                "\"clanTr1\":" + clanTr1 + "," +
                "\"player2\":\"" + player2 + "\"," +
                "\"level2\":" + level2 + "," +
                "\"all_deck2\":" + allDeck2 + "," +
                "\"deck2\":" + deck2 + "," +
                "\"clan2\":\"" + clan2 + "\"," +
                "\"crown2\":" + crown2 + "," +
                "\"touch2\":" + touch2 + "," +
                "\"elixir2\":" + elixir2 + "," +
                "\"exp2\":" + exp2 + "," +
                "\"expPoints2\":" + expPoints2 + "," +
                "\"cards2\":\"" + cards2 + "\"," +
                "\"cardScore2\":" + cardScore2 + "," +
                "\"clanTr2\":" + clanTr2 +
                "}";
    }
}
