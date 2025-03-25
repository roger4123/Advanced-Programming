package Compulsory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Location implements Comparable<Location> {
    private String name;
    protected LocationType type;

    public Location(String name, LocationType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public LocationType getType() {
        return type;
    }

    @Override
    public int compareTo(Location o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(name, location.name) && type == location.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
