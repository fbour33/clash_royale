import com.google.gson.Gson;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

public class DeckSummaryWritable implements Writable, Cloneable, Serializable {

    public String deckId;
    public long totalWins;
    public long totalUses;
    public long uniquePlayers;
    public long highestClanLevel;
    public double avgDeckStrength;
    public double winRate;

    public DeckSummaryWritable(){}

    public DeckSummaryWritable(String deckId, long totalWins, long totalUses,
                               long uniquePlayers, long highestClanLevel, double avgDeckStrength, double winRate){
        this.deckId = deckId;
        this.totalWins = totalWins;
        this.totalUses = totalUses;
        this.uniquePlayers = uniquePlayers;
        this.highestClanLevel = highestClanLevel;
        this.avgDeckStrength = avgDeckStrength;
        this.winRate = winRate;
    }


    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(deckId);
        out.writeLong(totalWins);
        out.writeLong(totalUses);
        out.writeLong(uniquePlayers);
        out.writeLong(highestClanLevel);
        out.writeDouble(avgDeckStrength);
        out.writeDouble(winRate);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        deckId = in.readUTF();
        totalWins = in.readLong();
        totalUses = in.readLong();
        uniquePlayers = in.readLong();
        highestClanLevel = in.readLong();
        avgDeckStrength = in.readDouble();
        winRate = in.readDouble();
    }

    @Override
    public DeckSummaryWritable clone(){
        try{
            DeckSummaryWritable clone = (DeckSummaryWritable) super.clone();
            clone.deckId = this.deckId;
            clone.totalWins = this.totalWins;
            clone.totalUses = this.totalUses;
            clone.uniquePlayers = this.uniquePlayers;
            clone.highestClanLevel = this.highestClanLevel;
            clone.avgDeckStrength = this.avgDeckStrength;
            clone.winRate = this.winRate;
            return clone;
        }catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
