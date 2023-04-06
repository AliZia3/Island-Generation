package ca.mcmaster.cas.se2aa4.a4.pathfinder.adt;

import java.util.List;
import java.util.Map;

public interface Graph {
    void addNode(Node node);
    void removeNode(Node node);
    void addEdge(Edge edge);
    void removeEdge(Edge edge);
    Map<Integer, Node> getNodes();
    List<Edge> getEdges();
    List<Edge> getEdgesForNode(Node node);
}
