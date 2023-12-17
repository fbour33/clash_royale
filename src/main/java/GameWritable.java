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
    @JsonProperty("win")
    long win;

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
    @JsonProperty("cards2")
    String cards2;
    @JsonProperty("cardScore2")
    long cardScore2;
    @JsonProperty("clanTr2")
    long clanTr2;


    public GameWritable(Instant date, long round, String type, String mode, long win,
                       String player1, long level1, double allDeck1, double deck1,
                       String clan1, String cards1, long cardScore1, long clanTr1,
                       String player2, long level2, double allDeck2, double deck2,
                       String clan2, String cards2, long cardScore2, long clanTr2) {
        this.date = date;
        this.round = round;
        this.type = type;
        this.mode = mode;
        this.win = win;
        this.player1 = player1;
        this.level1 = level1;
        this.allDeck1 = allDeck1;
        this.deck1 = deck1;
        this.clan1 = clan1;
        this.cards1 = cards1;
        this.cardScore1 = cardScore1;
        this.clanTr1 = clanTr1;
        this.player2 = player2;
        this.level2 = level2;
        this.allDeck2 = allDeck2;
        this.deck2 = deck2;
        this.clan2 = clan2;
        this.cards2 = cards2;
        this.cardScore2 = cardScore2;
        this.clanTr2 = clanTr2;
    }

    public void write(DataOutput out) throws IOException {
        out.writeLong(date.toEpochMilli());
        out.writeLong(round);
        out.write(type.getBytes());
        out.write(mode.getBytes());
        out.writeLong(win);
        out.write(player1.getBytes());
        out.writeLong(level1);
        out.writeDouble(allDeck1);
        out.writeDouble(deck1);
        out.write(clan1.getBytes());
        out.write(cards1.getBytes());
        out.writeLong(cardScore1);
        out.writeLong(clanTr1);
        out.write(cards1.getBytes());
        out.write(player2.getBytes());
        out.writeLong(level2);
        out.writeDouble(allDeck2);
        out.writeDouble(deck2);
        out.write(clan2.getBytes());
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
        win = in.readLong();
        player1= String.valueOf(in.readByte());
        level1= in.readLong();
        allDeck1= in.readDouble();
        deck1= in.readDouble();
        clan1= String.valueOf(in.readByte());
        cards1= String.valueOf(in.readByte());
        cardScore1=in.readLong();
        clanTr1=in.readLong();
        player2= String.valueOf(in.readByte());
        level2=in.readLong();
        allDeck2=in.readDouble();
        deck2=in.readDouble();
        clan2=String.valueOf(in.readByte());
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
                "\"win\":\"" + win + "\"," +
                "\"player1\":\"" + player1 + "\"," +
                "\"level1\":" + level1 + "," +
                "\"all_deck1\":" + allDeck1 + "," +
                "\"deck1\":" + deck1 + "," +
                "\"clan1\":\"" + clan1 + "\"," +
                "\"cards1\":\"" + cards1 + "\"," +
                "\"cardScore1\":" + cardScore1 + "," +
                "\"clanTr1\":" + clanTr1 + "," +
                "\"player2\":\"" + player2 + "\"," +
                "\"level2\":" + level2 + "," +
                "\"all_deck2\":" + allDeck2 + "," +
                "\"deck2\":" + deck2 + "," +
                "\"clan2\":\"" + clan2 + "\"," +
                "\"cards2\":\"" + cards2 + "\"," +
                "\"cardScore2\":" + cardScore2 + "," +
                "\"clanTr2\":" + clanTr2 +
                "}";
    }
}
