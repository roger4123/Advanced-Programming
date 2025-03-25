package Compulsory;

public class Path {
    private Location start;
    private Location destination;
    private double time;
    private double safetyProbability;

    public Path(Location start, Location destination, double time, double safetyProbability) {
        this.start = start;
        this.destination = destination;
        this.time = time;
        this.safetyProbability = safetyProbability;
    }

    public Location getStart() {
        return start;
    }

    public Location getDestination() {
        return destination;
    }

    public double getTime() {
        return time;
    }

    public double getSafetyProbability() {
        return safetyProbability;
    }

    @Override
    public String toString() {
        return "Path{" +
                "start=" + start +
                ", destination=" + destination +
                ", time=" + String.format("%.2f", time) +
                ", safetyProbability=" + String.format("%.2f", safetyProbability) +
                '}';
    }
}
