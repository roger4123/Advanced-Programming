package Homework;

import Bonus.Pair;
import Bonus.SafestRoute;
import Compulsory.Location;
import Compulsory.Path;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Robot {
    private List<Location> locations;
    private List<Path> paths;
    private Location start;

    public Robot(List<Location> locations, List<Path> paths, Location start) {
        this.locations = locations;
        this.paths = paths;
        this.start = start;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public Location getStart() {
        return start;
    }

    // BONUS
    public Map<Pair, SafestRoute> computeSafestRoutes() {
        Graph<Location, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        for (Location location : locations) {
            graph.addVertex(location);
        }

        for (Path path : paths) {
            if (!path.getStart().equals(path.getDestination())) {
                DefaultWeightedEdge weightedEdge = graph.addEdge(path.getStart(), path.getDestination());
                if (weightedEdge != null) {
                    graph.setEdgeWeight(weightedEdge, -Math.log(path.getSafetyProbability()));
                }
            }
        }

        Map<Pair, SafestRoute> safestRoutes = new HashMap<>();

        for (Location source : locations) {
            DijkstraShortestPath<Location, DefaultWeightedEdge> dsp = new DijkstraShortestPath<>(graph);

            for (Location destination : locations) {
                if(!source.equals(destination)) {
                    GraphPath<Location, DefaultWeightedEdge> graphPath = dsp.getPath(source, destination);

                    if(graphPath != null) {
                        double totalSafetyProbability = Math.exp(-graphPath.getWeight());

                        List<Location> path = graphPath.getVertexList();
                        Pair pair = new Pair(source,destination);

                        SafestRoute safestRoute = new SafestRoute(path, totalSafetyProbability);
                        safestRoutes.put(pair, safestRoute);
                    }
                }
            }
        }
        return safestRoutes;
    }
}
