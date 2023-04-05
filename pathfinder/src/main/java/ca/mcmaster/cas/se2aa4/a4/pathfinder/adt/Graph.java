package ca.mcmaster.cas.se2aa4.a4.pathfinder.adt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Integer, Node> nodes;
    private List<Edge> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    public void removeNode(Node node) {
        nodes.remove(node.getId());
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        Node source = edge.getSource();
        source.getOutgoingEdges().add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
        Node source = edge.getSource();
        source.getOutgoingEdges().remove(edge);
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Edge> getEdgesForNode(Node node) {
        List<Edge> edgesForNode = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)) {
                edgesForNode.add(edge);
            }
        }
        return edgesForNode;
    }
}