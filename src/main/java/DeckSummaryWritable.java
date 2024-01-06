import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DeckSummaryWritable implements Writable, Cloneable {

    public String deckId;
    public long totalWins;
    public long totalUses;
    public long uniquePlayers;
    public long highestClanLevel;
    public double avgDeckStrength;

    public DeckSummaryWritable(){}

    public DeckSummaryWritable(String deckId, long totalWins, long totalUses,
                               long uniquePlayers, long highestClanLevel, double avgDeckStrength){
        this.deckId = deckId;
        this.totalWins = totalWins;
        this.totalUses = totalUses;
        this.uniquePlayers = uniquePlayers;
        this.highestClanLevel = highestClanLevel;
        this.avgDeckStrength = avgDeckStrength;
    }


    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(deckId);
        out.writeLong(totalWins);
        out.writeLong(totalUses);
        out.writeLong(uniquePlayers);
        out.writeLong(highestClanLevel);
        out.writeDouble(avgDeckStrength);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        deckId = in.readUTF();
        totalWins = in.readLong();
        totalUses = in.readLong();
        uniquePlayers = in.readLong();
        highestClanLevel = in.readLong();
        avgDeckStrength = in.readDouble();
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
            return clone;
        }catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
}
