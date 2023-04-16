package ca.mcmaster.cas.se2aa4.a3.island.cities;

import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.DijkstrasAlgorithm;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.PathFinder;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.DirectedGraph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Node;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Edge;

public class StarNetwork {
    private Graph graph;
    private Structs.Mesh aMesh;
    private Random random;

    public StarNetwork(Graph graph, Structs.Mesh iMesh) {
        this.graph = graph;
        this.aMesh = iMesh;
        random = new Random();
        connectEdges();
        printPathBetweenCities();
    }

    // Should connect every node to its neighbors with an edge and should do this
    // for all edges
    public void connectEdges() {
        // Connecting all neighboring centroids with graph edges
        for (Structs.Polygon p : aMesh.getPolygonsList()) {
            Node node1 = new Node(p.getCentroidIdx(), "node", random.nextInt(1, 5 + 1));
            for (int idx : p.getNeighborIdxsList()) {
                Node node2 = new Node(aMesh.getPolygons(idx).getCentroidIdx(), "node", random.nextInt(0, 100 + 1));
                graph.addEdge(new Edge(node1, node2, random.nextInt(1, 5 + 1)));
            }
        }
    }

    // Should print the right path between capital to a city
    public void printPathBetweenCities() {
        System.out.println("===============================PATHFINDING===========================");
        PathFinder<Edge> algorithm = new DijkstrasAlgorithm();
        List<Edge> path = algorithm.findPath(graph.getNodes().get(0), graph.getNodes().get(1), graph);
        System.out.println("NAME FOR NODE 1: " + graph.getNodes().get(0).getName());
        System.out.println("NAME FOR NODE 2: " + graph.getNodes().get(1).getName());

        if (path.isEmpty()) {
            System.out.println("No path found");
        } else {
            double totalWeight = 0;
            System.out.println("Path found:");

            for (Edge edge : path) {
                int destinationID = edge.getDestination().getId();
                int sourceID = edge.getSource().getId();
                String sourceName = edge.getSource().getName();
                String destinationName = edge.getDestination().getName();
                double weight = edge.getWeight();

                System.out.println("(ID:" + sourceID + ") " + sourceName + " -> " + "(ID:" + destinationID + ") "
                        + destinationName + " (" + weight + ")");
                totalWeight += edge.getWeight();
            }
            System.out.println("Shortest Path Total Weight: " + totalWeight);
        }

        System.out.println("==================================================================");
    }

}
