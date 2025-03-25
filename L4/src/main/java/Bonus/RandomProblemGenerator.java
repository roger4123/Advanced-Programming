package Bonus;

import Compulsory.Location;
import Compulsory.LocationType;
import Compulsory.Path;
import Homework.Robot;
import com.github.javafaker.Faker;

import java.util.*;

public class RandomProblemGenerator {
    private Faker faker;
    private Random rand;

    public RandomProblemGenerator() {
        this.rand = new Random();
        this.faker = new Faker();
    }

    private List<Location> generateRandomLocations(int nrOfLocations) {
        List<Location> locations = new ArrayList<>(nrOfLocations);

        int countFriendlyLocations = (int) (nrOfLocations * 0.4);
        int countNeutralLocations = (int) (nrOfLocations * 0.3);
        int countEnemyLocations = nrOfLocations - countFriendlyLocations - countNeutralLocations;

        for (int i = 0; i < countFriendlyLocations; i++) {
            locations.add(new Location(faker.funnyName().name(), LocationType.FRIENDLY));
        }

        for (int i = 0; i < countNeutralLocations; i++) {
            locations.add(new Location(faker.funnyName().name(), LocationType.NEUTRAL));
        }

        for (int i = 0; i < countEnemyLocations; i++) {
            locations.add(new Location(faker.funnyName().name(), LocationType.ENEMY));
        }

        return locations;
    }

    private List<Path> generateRandomPaths(List<Location> locations, double connectivityDensity) {
        List<Path> paths = new ArrayList<>();

        for(int i = 0; i < locations.size(); i++) {
            for(int j = 0; j < locations.size(); j++) {
                if(i != j && rand.nextDouble() < connectivityDensity) {
                    paths.add(new Path(locations.get(i), locations.get(j), 1 + rand.nextDouble(10), 0.1 + rand.nextDouble() * 0.9));
                }
            }
        }
        return paths;
    }

    public Robot generateRandomProblem(int nrOfLocations, double connectivityDensity) {
        List<Location> locations = generateRandomLocations(nrOfLocations);
        List<Path> paths = generateRandomPaths(locations, connectivityDensity);

        Location startLocation = locations.get(rand.nextInt(locations.size()));

        return new Robot(locations, paths, startLocation);
    }
}
