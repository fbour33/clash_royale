import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SortingDeckWritable implements Writable, Cloneable {

    public String deckId;
    public String playerId;
    public long win;
    public double deckStrength;
    public long clanLevel;

    public SortingDeckWritable(){}

    public SortingDeckWritable(String deckId, String playerId, long win, double deckStrength, long clanLevel){
        this.deckId = deckId;
        this.playerId = playerId;
        this.win = win;
        this.deckStrength = deckStrength;
        this.clanLevel = clanLevel;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(deckId);
        out.writeUTF(playerId);
        out.writeLong(win);
        out.writeDouble(deckStrength);
        out.writeLong(clanLevel);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        deckId = in.readUTF();
        playerId = in.readUTF();
        win = in.readLong();
        deckStrength = in.readDouble();
        clanLevel = in.readLong();
    }

    @Override
    public SortingDeckWritable clone() {
        try {
            SortingDeckWritable clone = (SortingDeckWritable) super.clone();
            clone.deckId = this.deckId;
            clone.playerId = this.playerId;
            clone.win = this.win;
            clone.deckStrength = this.deckStrength;
            clone.clanLevel = this.clanLevel;
            return clone;
        }catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
