import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StatisticsWritable implements Writable {

    private long victories;
    private long uses;
    private long uniquePlayers;
    private long highestClanLevel;
    private double averageDiffForce;


    public StatisticsWritable(long victories, long uses, long uniquePlayers,
                              long highestClanLevel, double averageDiffForce) {
        this.victories = victories;
        this.uses = uses;
        this.uniquePlayers = uniquePlayers;
        this.highestClanLevel = highestClanLevel;
        this.averageDiffForce = averageDiffForce;
    }

    public long getVictories() {
        return victories;
    }

    public void setVictories(long victories) {
        this.victories = victories;
    }

    public long getUses() {
        return uses;
    }

    public void setUses(long uses) {
        this.uses = uses;
    }

    public long getUniquePlayers() {
        return uniquePlayers;
    }

    public void setUniquePlayers(long uniquePlayers) {
        this.uniquePlayers = uniquePlayers;
    }

    public long getHighestClanLevel() {
        return highestClanLevel;
    }

    public void setHighestClanLevel(long highestClanLevel) {
        this.highestClanLevel = highestClanLevel;
    }

    public double getAverageDiffForce() {
        return averageDiffForce;
    }

    public void setAverageDiffForce(double averageDiffForce) {
        this.averageDiffForce = averageDiffForce;
    }


    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(victories);
        out.writeLong(uses);
        out.writeLong(uniquePlayers);
        out.writeLong(highestClanLevel);
        out.writeDouble(averageDiffForce);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        victories = in.readLong();
        uses = in.readLong();
        uniquePlayers = in.readLong();
        highestClanLevel = in.readLong();
        averageDiffForce = in.readDouble();
    }

    @Override
    public String toString() {
        return String.format("Victories: %d" +
                        "\nUses: %d\nUnique Players: %d" +
                        "\nHighest Clan Level: %d" +
                        "\nAverage diff Force : %.2f",
                victories, uses, uniquePlayers, highestClanLevel, averageDiffForce);
    }
}
