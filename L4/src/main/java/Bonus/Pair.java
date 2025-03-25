package Bonus;

import Compulsory.Location;

import java.util.Objects;

public class Pair {
    private Location source;
    private Location target;
    private boolean directly;
    private double time;
    private double safetyProbability;

    public Pair(Location source, Location target) {
        this.source = source;
        this.target = target;
    }

    public Location getSource() {
        return source;
    }

    public Location getTarget() {
        return target;
    }

    public boolean isDirectly() {
        return directly;
    }

    public double getTime() {
        return time;
    }

    public double getSafetyProbability() {
        return safetyProbability;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return directly == pair.directly && Double.compare(time, pair.time) == 0 && Double.compare(safetyProbability, pair.safetyProbability) == 0 && Objects.equals(source, pair.source) && Objects.equals(target, pair.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, directly, time, safetyProbability);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "source=" + source +
                ", target=" + target +
                ", directly=" + directly +
                ", time=" + time +
                ", safetyProbability=" + safetyProbability +
                '}';
    }
}
