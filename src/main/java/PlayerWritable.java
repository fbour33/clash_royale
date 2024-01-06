import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PlayerWritable implements Writable, Cloneable {

    
    String playerId;
    long level;
    double deck;
    double allDeck;
    String clanId;
    long crown;
    long exp;
    long expPoints;
    String cards;

    long cardScore;
    long clanTr;

    public PlayerWritable(){}

    public PlayerWritable(String playerId, long level, double deck, double allDeck, String clanId, long crown,
                          long exp, long expPoints, String cards, long cardScore, long clanTr) {
        this.playerId = playerId;
        this.level = level;
        this.deck = deck;
        this.allDeck = allDeck;
        this.clanId = clanId;
        this.crown = crown;
        this.exp = exp;
        this.expPoints = expPoints;
        this.cards = cards;
        this.cardScore = cardScore;
        this.clanTr = clanTr;
    }

//    public void setPlayerFields(String playerId, long level, double deck, double allDeck, String clanId, long crown,
//                                long exp, long expPoints, String cards, long cardScore, long clanTr){
//        this.playerId = playerId;
//        this.level = level;
//        this.deck = deck;
//        this.allDeck = allDeck;
//        this.clanId = clanId;
//        this.crown = crown;
//        this.exp = exp;
//        this.expPoints = expPoints;
//        this.cards = cards;
//        this.cardScore = cardScore;
//        this.clanTr = clanTr;
//    }
    

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(playerId);
        out.writeLong(level);
        out.writeDouble(deck);
        out.writeDouble(allDeck);
        out.writeUTF(clanId);
        out.writeLong(crown);
        out.writeLong(exp);
        out.writeLong(expPoints);
        out.writeUTF(cards);
        out.writeLong(cardScore);
        out.writeLong(clanTr);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        playerId = in.readUTF();
        level = in.readLong();
        deck = in.readDouble();
        allDeck = in.readDouble();
        clanId = in.readUTF();
        crown = in.readLong();
        exp = in.readLong();
        expPoints = in.readLong();
        cards = in.readUTF();
        cardScore = in.readLong();
        clanTr = in.readLong();
    }

    @Override
    public PlayerWritable clone() {
        try{
            PlayerWritable clone = (PlayerWritable) super.clone();
            clone.playerId = this.playerId;
            clone.level = this.level;
            clone.deck = this.deck;
            clone.allDeck = this.allDeck;
            clone.clanId = this.clanId;
            clone.crown = this.crown;
            clone.exp = this.exp;
            clone.expPoints = this.expPoints;
            clone.cards = this.cards;
            clone.cardScore = this.cardScore;
            clone.clanTr = this.clanTr;
            return clone;
        }catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

//    @Override
//    public String toString() {
//        return "{" +
//                 "\"player\":\"" + playerId + "\"," +
//                "\"level\":" + level + "," +
//                "\"deck\":" + deck + "," +
//                "\"all_deck\":" + allDeck + "," +
//                "\"clan\":\"" + clanId + "\"," +
//                "\"crown\":" + crown + "," +
//                "\"exp\":" + exp + "," +
//                "\"expPoints\":" + expPoints + "," +
//                "\"cards\":\"" + cards + "\"," +
//                "\"cardScore\":" + cardScore + "," +
//                "\"clanTr\":" + clanTr +
//                "}";
//    }
}
