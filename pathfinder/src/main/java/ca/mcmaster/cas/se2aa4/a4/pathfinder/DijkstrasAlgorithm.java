package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Node;

public class DijkstrasAlgorithm implements PathFinder {
    private Graph graph;

    public DijkstrasAlgorithm(Graph graph) {
        this.graph = graph;
    }

    // Prof stated we can use sources for the pathfinding algorithm as long as we cite them
    // Got most of the bare bones ideas from here
    // https://github.com/rabestro/graph-pathfinding-algorithms/blob/master/algorithm/src/main/java/lv/id/jc/algorithm/graph/DijkstrasAlgorithm.java
    @Override
    public List<Edge> findPath(Node source, Node destination) {
        Deque<Node> queue = new ArrayDeque<>();
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Edge> previous = new HashMap<>();
        queue.add(source);
        distances.put(source, 0.0);

        while (!queue.isEmpty()) {
            Node prev = queue.removeFirst();
            for (Edge edge : graph.getEdgesForNode(prev)) {
                Node node = edge.getDestination();
                double distance = distances.get(prev) + edge.getWeight();
                if (distance < distances.getOrDefault(node, Double.MAX_VALUE)) {
                    previous.put(node, edge);
                    distances.put(node, distance);
                    queue.addLast(node);
                }
            }
        }
        if (previous.containsKey(destination) || source.equals(destination)) {
            LinkedList<Edge> path = new LinkedList<>();
            Node current = destination;
            while (previous.get(current) != null) {
                Edge edge = previous.get(current);
                path.addFirst(edge);
                current = edge.getSource();
            }
            return path;
        } else {
            return Collections.emptyList();
        }
    }
}