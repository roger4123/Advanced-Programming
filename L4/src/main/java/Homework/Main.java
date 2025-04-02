package Homework;

import Compulsory.Location;
import Compulsory.LocationType;
import Compulsory.Path;
import com.github.javafaker.Faker;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();
        List<Location> locations = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(1,10); i++) {
            locations.add(new Location(faker.funnyName().name(), LocationType.FRIENDLY));
        }
        for (int i = 0; i < rand.nextInt(10); i++) {
            locations.add(new Location(faker.funnyName().name(), LocationType.NEUTRAL));
        }
        for (int i = 0; i < rand.nextInt(10); i++) {
            locations.add(new Location(faker.funnyName().name(), LocationType.ENEMY));
        }

        System.out.println("\nLOCATIONS:\n");
        for(Location loc : locations) {
            System.out.println(loc.toString());
        }

        List<Path> paths = generateRandomPaths(locations);

        System.out.println("\nPATHS:\n");
        for(Path p : paths) {
            System.out.println(p.toString());
        }

        Location startLocation = locations.getFirst();
        Robot robotMap = new Robot(locations, paths, startLocation);

        Map<LocationType, List<Location>> locationsOfType = locations.stream().collect(Collectors.groupingBy(Location::getType));

        Map<Location, Double> fastestTimes = robotMap.computeFastestTimes();

        System.out.println("\nFastest times from " + startLocation.getName() + ":\n");
        System.out.println("■ to friendly locations: ");
        locationsOfType.get(LocationType.FRIENDLY).stream().filter(loc -> !loc.equals(startLocation)).forEach(loc -> System.out.println(loc.getName() + ": " + fastestTimes.get(loc) + "s"));

        System.out.println("\n■ to neutral locations: ");
        locationsOfType.get(LocationType.NEUTRAL).forEach(loc -> System.out.println(loc.getName() + ": " + fastestTimes.get(loc) + "s"));

        System.out.println("\n■ to enemy locations: ");
        locationsOfType.get(LocationType.ENEMY).forEach(loc -> System.out.println(loc.getName() + ": " + fastestTimes.get(loc) + "s"));

    }

    private static List<Path> generateRandomPaths(List<Location> locations) {
        List<Path> paths = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                if (i != j && random.nextDouble() < 0.3) {
                    paths.add(new Path(locations.get(i), locations.get(j), 1 + random.nextDouble(10.0), 0.1 + random.nextDouble() * 0.9));
                }
            }
        }

        return paths;
    }


}
