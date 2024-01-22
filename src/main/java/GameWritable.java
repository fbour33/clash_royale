import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.time.Instant;

public class GameWritable implements Writable, Cloneable {

    
    public Instant date;
    
    public long round;
    
    public String type;
    
    public String mode;
    
    public long win;

    public PlayerWritable player1;
    public PlayerWritable player2;

    public GameWritable(){}

    public GameWritable(Instant date, long round, String type, String mode, long win,
                        PlayerWritable player1, PlayerWritable player2) {
        this.date = date;
        this.round = round;
        this.type = type;
        this.mode = mode;
        this.win = win;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(date.toEpochMilli());
        out.writeLong(round);
        out.writeUTF(type);
        out.writeUTF(mode);
        out.writeLong(win);
        player1.write(out);
        player2.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        date = Instant.ofEpochMilli(in.readLong());
        round = in.readLong();
        type = in.readUTF();
        mode = in.readUTF();
        win = in.readLong();
        player1 = new PlayerWritable();
        player1.readFields(in);
        player2 = new PlayerWritable();
        player2.readFields(in);
    }

    @Override
    public GameWritable clone() {
        try{
            GameWritable clone = (GameWritable) super.clone();
            clone.date = Instant.from(date);
            clone.round = this.round;
            clone.type = this.type;
            clone.mode = this.mode;
            clone.win = this.win;
            clone.player1 = (PlayerWritable) this.player1.clone();
            clone.player2 = (PlayerWritable) this.player2.clone();
            return clone;
        }catch(CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

//    @Override
//    public String toString() {
//        return "{" +
//                "\"date\":\"" + date + "\"," +
//                "\"round\":" + round + "," +
//                "\"type\":\"" + type + "\"," +
//                "\"mode\":\"" + mode + "\"," +
//                "\"win\": " + win + "," +
//                "\"player1\": " + player1.toString() + "," +
//                "\"player2\": " + player2.toString() +
//                "}";
//    }
}
