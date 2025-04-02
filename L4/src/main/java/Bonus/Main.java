package Bonus;

import Compulsory.Location;
import Compulsory.LocationType;
import Homework.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static final int NR_LOCATIONS = 10;
    public static final double DENSITY = 0.3;

    public static void main(String[] args) {
        runDemo();
        runPerformanceTests();
    }

    private static void runDemo()

    {
        System.out.println("-----SMALL TEST-----");

        RandomProblemGenerator pg = new RandomProblemGenerator();
        Robot map = pg.generateRandomProblem(NR_LOCATIONS, DENSITY);

        Map<LocationType, List<Location>> locationsByType = map.getLocations().stream().collect(Collectors.groupingBy(Location::getType));

        System.out.println("\nFriendly locations:");
        locationsByType.getOrDefault(LocationType.FRIENDLY, List.of()).forEach(System.out::println);

        System.out.println("\nNeutral locations:");
        locationsByType.getOrDefault(LocationType.NEUTRAL, List.of()).forEach(System.out::println);

        System.out.println("\nEnemy locations:");
        locationsByType.getOrDefault(LocationType.ENEMY, List.of()).forEach(System.out::println);

        Map<Pair, SafestRoute> safestRoutes = map.computeSafestRoutes();
        System.out.println("\nSample of safest routes:");
        safestRoutes.entrySet().stream().limit(3).forEach(entry -> {
            System.out.println("\nRoute from " +
                    entry.getKey().toString() + ":");
            System.out.println(entry.getValue());
        });
    }

    private static void runPerformanceTests() {
        System.out.println("-----PERFORMANCE TESTS-----");

        PerformanceTest tester = new PerformanceTest(new RandomProblemGenerator(), new ArrayList<>());
        int[] problemSizes = {100, 500, 1000};
        double[] connectivityDensities = {0.01, 0.05, 0.1};

        tester.run(problemSizes, connectivityDensities);
        tester.printStatistics();
    }
}
