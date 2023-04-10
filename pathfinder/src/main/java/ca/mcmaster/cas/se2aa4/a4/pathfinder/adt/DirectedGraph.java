package ca.mcmaster.cas.se2aa4.a4.pathfinder.adt;

import java.util.ArrayList;
import java.util.List;

public class DirectedGraph implements Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public DirectedGraph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public void addNode(Node node) {
        nodes.add(node);
    }

    @Override
    public void removeNode(Node node) {
        nodes.remove(node);
    }

    @Override
    public void addEdge(Edge edge) {
        edges.add(edge);
        Node source = edge.getSource();
        source.addOutgoingEdge(edge);
    }

    @Override
    public void removeEdge(Edge edge) {
        edges.remove(edge);
        Node source = edge.getSource();
        source.removeOutgoingEdge(edge);
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public List<Edge> getEdgesForNode(Node node) {
        return node.getOutgoingEdges();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nodes:\n");
        for (Node node : nodes) {
            sb.append(node.toString()).append("\n");
        }
        sb.append("Edges:\n");
        for (Edge edge : edges) {
            sb.append(edge.toString()).append("\n");
        }
        return sb.toString();
    }
}