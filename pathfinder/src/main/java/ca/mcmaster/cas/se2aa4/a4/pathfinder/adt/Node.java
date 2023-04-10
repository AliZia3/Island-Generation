package ca.mcmaster.cas.se2aa4.a4.pathfinder.adt;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int id;
    private String name;
    private int elevation;
    private List<Edge> outgoingEdges;

    public Node(int id, String name, int elevation) {
        this.id = id;
        this.name = name;
        this.elevation = elevation;
        this.outgoingEdges = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getElevation() {
        return elevation;
    }

    public void addOutgoingEdge(Edge edge) {
        outgoingEdges.add(edge);
    }

    public void removeOutgoingEdge(Edge edge) {
        outgoingEdges.remove(edge);
    }

    public List<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node ").append(name).append(": [");
        for (Edge edge : outgoingEdges) {
            sb.append(edge.toString()).append(", ");
        }
        if (outgoingEdges.size() > 0) {
            sb.delete(sb.length() - 2, sb.length()); // remove last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
}