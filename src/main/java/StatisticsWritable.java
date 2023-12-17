import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StatisticsWritable implements Writable {

    private long totalVictories;
    private long totalUses;
    private long uniquePlayers;
    private long highestClanLevel;
    private double averageStrength;


    public StatisticsWritable(long totalVictories, long totalUses, long uniquePlayers,
                              long highestClanLevel, double averageStrengthDifference) {
        this.totalVictories = totalVictories;
        this.totalUses = totalUses;
        this.uniquePlayers = uniquePlayers;
        this.highestClanLevel = highestClanLevel;
        this.averageStrength = averageStrengthDifference;
    }

    public long getTotalVictories() {
        return totalVictories;
    }

    public void setTotalVictories(long totalVictories) {
        this.totalVictories = totalVictories;
    }

    public long getTotalUses() {
        return totalUses;
    }

    public void setTotalUses(long totalUses) {
        this.totalUses = totalUses;
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

    public void setHighestClanLevel(int highestClanLevel) {
        this.highestClanLevel = highestClanLevel;
    }

    public double getAverageStrength() {
        return averageStrength;
    }

    public void setAverageStrength(double averageStrength) {
        this.averageStrength = averageStrength;
    }


    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(totalVictories);
        out.writeLong(totalUses);
        out.writeLong(uniquePlayers);
        out.writeLong(highestClanLevel);
        out.writeDouble(averageStrength);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        totalVictories = in.readLong();
        totalUses = in.readLong();
        uniquePlayers = in.readLong();
        highestClanLevel = in.readLong();
        averageStrength = in.readDouble();
    }

    @Override
    public String toString() {
        return String.format("Total Victories: %d" +
                        "\nTotal Uses: %d\nUnique Players: %d" +
                        "\nHighest Clan Level: %d" +
                        "\nAverage Strength: %.2f",
                totalVictories, totalUses, uniquePlayers, highestClanLevel, averageStrength);
    }
}
