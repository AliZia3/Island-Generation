package ca.mcmaster.cas.se2aa4.a4.pathfinder.adt;

import java.util.List;

public interface Graph {
    void addNode(Node node);
    void removeNode(Node node);
    void addEdge(Edge edge);
    void removeEdge(Edge edge);
    List<Node> getNodes();
    List<Edge> getEdges();
    List<Edge> getEdgesForNode(Node node);
}
