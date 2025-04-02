package Bonus;

import Compulsory.Location;
import Compulsory.LocationType;
import Homework.Robot;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PerformanceTest{
    private final RandomProblemGenerator rpg;
    private final List<PerformanceResult> results;

    public PerformanceTest(RandomProblemGenerator rpg, List<PerformanceResult> results) {
        this.rpg = rpg;
        this.results = results;
    }

    private PerformanceResult testPerformance(int problemSize, double connectivityDensity) {
        PerformanceResult result = new PerformanceResult(problemSize, connectivityDensity);
        Robot map = rpg.generateRandomProblem(problemSize, connectivityDensity);

        long startTime = System.currentTimeMillis();
        Map<Location, Double> fastestTimes = map.computeFastestTimes();
        long endTime = System.currentTimeMillis();
        result.setFastestRoutesComputeTime(endTime - startTime);

        long startTime2 = System.currentTimeMillis();
        Map<Pair, SafestRoute> safestRoutes = map.computeSafestRoutes();
        long endTime2 = System.currentTimeMillis();
        result.setSafestRoutesComputeTime(endTime2 - startTime2);

        // statistics
        if(!safestRoutes.isEmpty()) {
            double averageRouteLength = safestRoutes.values().stream().mapToDouble(info -> info.getPath().size()).average().orElse(0);
            result.setAverageRouteLength(averageRouteLength);

            double averageRouteSafety = safestRoutes.values().stream().mapToDouble(SafestRoute::getTotalSafety).average().orElse(0);
            result.setAverageRouteSafety(averageRouteSafety);

            Map<LocationType, Double> averageTypeCountPerRoute = new EnumMap<>(LocationType.class);
            for(LocationType type : LocationType.values()) {
                double averageTypeCount = safestRoutes.values().stream().mapToLong(info -> info.getCountLocationType().getOrDefault(type, 0L)).average().orElse(0);
                averageTypeCountPerRoute.put(type, averageTypeCount);
            }
            result.setAverageTypeCountPerRoute(averageTypeCountPerRoute);
        }
        return result;
    }

    public void run(int[] problemSizes, double[] connectivityDensities){
        for (int size : problemSizes) {
            for (double density : connectivityDensities) {
                System.out.println("Testing problem size: " + size +
                        " with connectivity density: " + density);
                PerformanceResult result = testPerformance(size, density);
                results.add(result);
                System.out.println(result);
                System.out.println("-----------------------------------");
            }
        }
    }

    public void printStatistics() {
        System.out.println("--------PERFORMANCE STATISTICS--------" + "\nAverage computation times:");
        Map<Integer, Double> averageFastestTimeBySize = results.stream().collect(Collectors.groupingBy(PerformanceResult::getProblemSize, Collectors.averagingLong(PerformanceResult::getFastestRoutesComputeTime)));
        Map<Integer, Double> averageSafestTimeBySize = results.stream().collect(Collectors.groupingBy(PerformanceResult::getProblemSize, Collectors.averagingLong(PerformanceResult::getSafestRoutesComputeTime)));

        averageFastestTimeBySize.forEach((size, time) -> System.out.println("Size " + size + " locations: " +
                "Fastest routes: " + String.format("%.2f", time) + " ms, " +
                "Safest routes: " + String.format("%.2f", averageSafestTimeBySize.get(size)) + " ms"));

        System.out.println("\nAverage route safety by connectivity density:");
        Map<Double,Double> averageSafetyByDensity = results.stream().collect(Collectors.groupingBy(PerformanceResult::getConnectivityDensity, Collectors.averagingDouble(PerformanceResult::getAverageRouteSafety)));

        averageSafetyByDensity.forEach((density, safety) -> System.out.println("Density " + String.format("%.2f", density) +
                ": Safety " + String.format("%.4f", safety)));

        double averageFriendlyLocations = results.stream().mapToDouble(r -> r.getAverageTypeCountPerRoute().getOrDefault(LocationType.FRIENDLY, 0.0)).average().orElse(0);
        System.out.println("Average friendly locations: " + String.format("%.2f", averageFriendlyLocations));

        double averageNeutralLocations = results.stream().mapToDouble(r -> r.getAverageTypeCountPerRoute().getOrDefault(LocationType.NEUTRAL, 0.0)).average().orElse(0);
        System.out.println("Average neutral locations: " + String.format("%.2f", averageNeutralLocations));

        double averageEnemyLocations = results.stream().mapToDouble(r -> r.getAverageTypeCountPerRoute().getOrDefault(LocationType.ENEMY, 0.0)).average().orElse(0);
        System.out.println("Average enemy locations: " + String.format("%.2f", averageEnemyLocations));

    }
}
