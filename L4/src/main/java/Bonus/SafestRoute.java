package Bonus;

import Compulsory.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SafestRoute {
    private List<Location> path;
    private double totalSafety;
    private Map<LocationType, Long> countLocationType;

    public SafestRoute(List<Location> path, double totalSafety) {
        this.path = path;
        this.totalSafety = totalSafety;
        this.countLocationType = path.stream().collect(Collectors.groupingBy(Location::getType,Collectors.counting()));
    }

    public List<Location> getPath() {
        return path;
    }

    public double getTotalSafety() {
        return totalSafety;
    }

    public Map<LocationType, Long> getCountLocationType() {
        return countLocationType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Path: ");
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i).getName());
            if (i < path.size() - 1) {
                sb.append(" → ");
            }
        }
        sb.append("\nOverall safety: ").append(String.format("%.2f", totalSafety));
        sb.append("\nLocation counts: ");
        countLocationType.forEach((type, count) -> sb.append(type).append("=").append(count).append(" "));
        return sb.toString();
    }
}
