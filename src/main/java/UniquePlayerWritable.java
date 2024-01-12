import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class UniquePlayerWritable implements Writable, Cloneable {

    public String deckId;
    public String playerId;
    public UniquePlayerWritable(){}
    public UniquePlayerWritable(String deckId, String playerId){
        this.deckId = deckId;
        this.playerId = playerId;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(deckId);
        out.writeUTF(playerId);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        deckId = in.readUTF();
        playerId = in.readUTF();
    }

    @Override
    public UniquePlayerWritable clone() {
        try {
            UniquePlayerWritable clone = (UniquePlayerWritable) super.clone();
            clone.deckId = this.deckId;
            clone.playerId = this.playerId;
            return clone;
        }catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        UniquePlayerWritable other = (UniquePlayerWritable) obj;
        return this.deckId.equals(other.deckId) && this.playerId.equals(other.playerId);
        //Objects.equals(deckId, other.deckId) && Objects.equals(playerId, other.playerId)
    }

    @Override
    public int hashCode() {
        return Objects.hash(deckId, playerId);
    }
}
