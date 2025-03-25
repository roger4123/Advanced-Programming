package Bonus;

import Compulsory.Location;
import Compulsory.LocationType;
import Compulsory.Path;
import Homework.Robot;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PerformanceTest{
    private RandomProblemGenerator rpg;
    private List<PerformanceResult> results;

    public PerformanceTest(RandomProblemGenerator rpg, List<PerformanceResult> results) {
        this.rpg = rpg;
        this.results = results;
    }

    public static Map<Location, Double> computeFastestTimes(Robot robotMap) {
        Graph<Location, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        for(Location loc : robotMap.getLocations()) {
            graph.addVertex(loc);
        }

        for(Path path : robotMap.getPaths()) {
            if (!path.getStart().equals(path.getDestination())) {
                DefaultWeightedEdge weightedEdge = graph.addEdge(path.getStart(), path.getDestination());
                if(weightedEdge != null) {
                    graph.setEdgeWeight(weightedEdge, path.getTime());
                }
            }
        }

        // compute the shortest paths using Dijkstra Algorithm ( from start to all other locations )
        DijkstraShortestPath<Location, DefaultWeightedEdge> dsp = new DijkstraShortestPath<>(graph);
        Map<Location, Double> fastestTimes = new HashMap<>();

        for(Location loc : robotMap.getLocations()) {
            if(!loc.equals(robotMap.getStart())) {
                double shortestPathWeight = dsp.getPathWeight(robotMap.getStart(), loc);
                if(Double.isFinite(shortestPathWeight)) {
                    fastestTimes.put(loc, Math.floor(shortestPathWeight * 100) / 100);
                }
                else {
                    fastestTimes.put(loc, Double.MAX_VALUE);
                }
            }
        }
        return fastestTimes;
    }

    private PerformanceResult testPerformance(int problemSize, double connectivityDensity) {
        PerformanceResult result = new PerformanceResult(problemSize, connectivityDensity);
        Robot map = rpg.generateRandomProblem(problemSize, connectivityDensity);

        long startTime = System.currentTimeMillis();
        Map<Location, Double> fastestTimes = computeFastestTimes(map);
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
