package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Node;

public interface PathFinder {
    List<Edge> findPath(Node source, Node destination, Graph graph);
}