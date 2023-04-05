package ca.mcmaster.cas.se2aa4.a4.pathfinder.adt;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int id;
    private String name;
    private int elevation;
    private List<Edge> outgoingEdges; // outgoing edges

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

    public List<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }
}