package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.Arrays;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.DijkstrasAlgorithm;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Node;

public class SamplePath {
    public void Paths() {
        Graph graph = new Graph();
        Node nodeA = new Node(1, "NY", 100);
        Node nodeB = new Node(2, "Toronto", 500);
        Node nodeC = new Node(3, "Silicon Valley", 50);
        Node nodeD = new Node(4, "California", 200);
        Node nodeE = new Node(5, "Mississauga", 1000);

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);


        graph.addEdge(new Edge(nodeA, nodeB, 1));
        graph.addEdge(new Edge(nodeA, nodeC, 2));
        graph.addEdge(new Edge(nodeC, nodeA, 3));
        graph.addEdge(new Edge(nodeC, nodeD, 4));
        graph.addEdge(new Edge(nodeD, nodeE, 5));
        

        // Edge edge1 = new Edge(nodeA, nodeB, 7);
        // Edge edge2 = new Edge(nodeA, nodeC, 3);
        // Edge edge3 = new Edge(nodeB, nodeD, 4);
        // Edge edge4 = new Edge(nodeC, nodeD, 1);
        // Edge edge5 = new Edge(nodeD, nodeE, 5);

        // graph.addEdge(edge1);
        // graph.addEdge(edge2);
        // graph.addEdge(edge3);
        // graph.addEdge(edge4);
        // graph.addEdge(edge5);

        DijkstrasAlgorithm pathFinder = new DijkstrasAlgorithm(graph);
        List<Edge> path = pathFinder.findPath(nodeA, nodeE);

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
                

                System.out.println("(ID:" + sourceID + ") " + sourceName + " -> " + "(ID:" + destinationID + ") " + destinationName + " ("+ weight + ")");
                // System.out.println(edge.getSource().getName() + " -> " + edge.getDestination().getName() + " ("+ edge.getWeight() + ")");
                totalWeight += edge.getWeight();

                
            }
            System.out.println("Shortest Path Total Weight: " + totalWeight);
        }
    }
}
