package Bonus;

import Compulsory.LocationType;

import java.util.Map;

public class PerformanceResult {
    private int problemSize;
    private double connectivityDensity;
    private long fastestRoutesComputeTime;
    private long safestRoutesComputeTime;
    private double averageRouteLength;
    private double averageRouteSafety;
    private Map<LocationType, Double> averageTypeCountPerRoute;

    public PerformanceResult(int problemSize, double connectivityDensity) {
        this.problemSize = problemSize;
        this.connectivityDensity = connectivityDensity;
    }

    public int getProblemSize() {
        return problemSize;
    }

    public void setProblemSize(int problemSize) {
        this.problemSize = problemSize;
    }

    public double getConnectivityDensity() {
        return connectivityDensity;
    }

    public void setConnectivityDensity(double connectivityDensity) {
        this.connectivityDensity = connectivityDensity;
    }

    public long getFastestRoutesComputeTime() {
        return fastestRoutesComputeTime;
    }

    public void setFastestRoutesComputeTime(long fastestRoutesComputeTime) {
        this.fastestRoutesComputeTime = fastestRoutesComputeTime;
    }

    public long getSafestRoutesComputeTime() {
        return safestRoutesComputeTime;
    }

    public void setSafestRoutesComputeTime(long safestRoutesComputeTime) {
        this.safestRoutesComputeTime = safestRoutesComputeTime;
    }

    public double getAverageRouteLength() {
        return averageRouteLength;
    }

    public void setAverageRouteLength(double averageRouteLength) {
        this.averageRouteLength = averageRouteLength;
    }

    public double getAverageRouteSafety() {
        return averageRouteSafety;
    }

    public void setAverageRouteSafety(double averageRouteSafety) {
        this.averageRouteSafety = averageRouteSafety;
    }

    public Map<LocationType, Double> getAverageTypeCountPerRoute() {
        return averageTypeCountPerRoute;
    }

    public void setAverageTypeCountPerRoute(Map<LocationType, Double> averageTypeCountPerRoute) {
        this.averageTypeCountPerRoute = averageTypeCountPerRoute;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Problem size: ").append(problemSize).append(" locations\n");
        sb.append("Connectivity density: ").append(String.format("%.2f", connectivityDensity)).append("\n");
        sb.append("Fastest routes computation time: ").append(fastestRoutesComputeTime).append(" ms\n");
        sb.append("Safest routes computation time: ").append(safestRoutesComputeTime).append(" ms\n");
        sb.append("Average route length: ").append(String.format("%.2f", averageRouteLength)).append(" locations\n");
        sb.append("Average route safety: ").append(String.format("%.4f", averageRouteSafety)).append("\n");
        sb.append("Average location type counts per route:\n");
        averageTypeCountPerRoute.forEach((type, count) -> sb.append("  ").append(type).append(": ").append(String.format("%.2f", count)).append("\n"));
        return sb.toString();
    }
}

