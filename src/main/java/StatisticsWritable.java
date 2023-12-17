import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StatisticsWritable implements Writable {

    private int totalVictories;
    private int totalUses;
    private int uniquePlayers;
    private int highestClanLevel;
    private double averageStrength;


    public StatisticsWritable(int totalVictories, int totalUses, int uniquePlayers,
                             int highestClanLevel, double averageStrengthDifference) {
        this.totalVictories = totalVictories;
        this.totalUses = totalUses;
        this.uniquePlayers = uniquePlayers;
        this.highestClanLevel = highestClanLevel;
        this.averageStrength = averageStrengthDifference;
    }

    public int getTotalVictories() {
        return totalVictories;
    }

    public void setTotalVictories(int totalVictories) {
        this.totalVictories = totalVictories;
    }

    public int getTotalUses() {
        return totalUses;
    }

    public void setTotalUses(int totalUses) {
        this.totalUses = totalUses;
    }

    public int getUniquePlayers() {
        return uniquePlayers;
    }

    public void setUniquePlayers(int uniquePlayers) {
        this.uniquePlayers = uniquePlayers;
    }

    public int getHighestClanLevel() {
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
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(totalVictories);
        dataOutput.writeInt(totalUses);
        dataOutput.writeInt(uniquePlayers);
        dataOutput.writeInt(highestClanLevel);
        dataOutput.writeDouble(averageStrength);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        totalVictories = dataInput.readInt();
        totalUses = dataInput.readInt();
        uniquePlayers = dataInput.readInt();
        highestClanLevel = dataInput.readInt();
        averageStrength = dataInput.readDouble();
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
