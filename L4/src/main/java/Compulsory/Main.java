package Compulsory;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Location[] locations = new Location[10];
        String[] locationName = {"A", "B", "C", "D", "E", "F", "G", "H" , "I", "J"};

        Random random = new Random();
        LocationType[] randomLocation = LocationType.values();
        for (int i = 0; i < 10; i++) {
            LocationType randomType = randomLocation[random.nextInt(randomLocation.length)];
            locations[i] = new Location(locationName[i], randomType);
            System.out.println(locations[i].toString());
        }

        TreeSet<Location> friendlyLocations =(TreeSet<Location>) Arrays.stream(locations)
                .filter(location -> location.getType() == LocationType.FRIENDLY)
                .collect(Collectors.toCollection(TreeSet::new));

        LinkedList<Location> enemyLocations = Arrays.stream(locations)
                .filter(location -> location.getType() == LocationType.ENEMY)
                .sorted(Comparator.comparing(Location::getType).thenComparing(Location::getName))
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println("\n" + friendlyLocations);
        System.out.println(enemyLocations);
    }
}
