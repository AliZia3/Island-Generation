import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.DijkstrasAlgorithm;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.PathFinder;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Node;

public class PathFindingTest {
    private Graph graph;
    private Node nodeA;
    private Node nodeB;
    private Node nodeC;
    private Node nodeD;
    private Node nodeE;
    private Node nodeExcluded;
    private Edge edgeAB;
    private Edge edgeAC;
    private Edge edgeCB;
    private Edge edgeBD;
    private Edge edgeDE;
    private PathFinder dijkstra;

    @Before
    public void setUp() {
        graph = new Graph();
        nodeA = new Node(1, "Toronto", 100);
        nodeB = new Node(2, "Montreal", 500);
        nodeC = new Node(3, "Vancouver", 50);
        nodeD = new Node(4, "Calgary", 200);
        nodeE = new Node(5, "Ottawa", 1000);
        nodeExcluded = new Node(6, "Texas", 750);

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeExcluded); // No edges to this node

        edgeAB = new Edge(nodeA, nodeB, 1);
        edgeAC = new Edge(nodeA, nodeC, 2);
        edgeCB = new Edge(nodeC, nodeB, 3);
        edgeBD = new Edge(nodeB, nodeD, 4);
        edgeDE = new Edge(nodeD, nodeE, 5);

        graph.addEdge(edgeAB);
        graph.addEdge(edgeAC);
        graph.addEdge(edgeCB);
        graph.addEdge(edgeBD);
        graph.addEdge(edgeDE);

        dijkstra = new DijkstrasAlgorithm(graph);

        // edgeAB = new Edge(nodeA, nodeB, 1);
        // edgeAC = new Edge(nodeA, nodeC, 3);
        // edgeBC = new Edge(nodeB, nodeC, 1);
        // edgeBD = new Edge(nodeB, nodeD, 2);
        // edgeCD = new Edge(nodeC, nodeD, 1);
        // edgeDE = new Edge(nodeD, nodeE, 2);
        // edgeEC = new Edge(nodeE, nodeC, 1);

        // graph.addEdge(edgeAB);
        // graph.addEdge(edgeAC);
        // graph.addEdge(edgeBC);
        // graph.addEdge(edgeBD);
        // graph.addEdge(edgeCD);
        // graph.addEdge(edgeDE);
        // graph.addEdge(edgeEC);
    }

    @Test
    public void testPathFinder_withValidNodes_shouldReturnCorrectShortestPath() {
        // Longest path is from edgeAC, edgeCB, edgeBD, edgeDE
        List<Edge> expectedPath = Arrays.asList(edgeAB, edgeBD, edgeDE);
        List<Edge> actualPath = dijkstra.findPath(nodeA, nodeE);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testPathFinder_withDifferentStartingNode_shouldReturnCorrectShortestPath() {
        List<Edge> expectedPath = Arrays.asList(edgeBD, edgeDE);
        List<Edge> actualPath = dijkstra.findPath(nodeB, nodeE);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testPathFinder_withSameSourceAndTargetNodes_shouldReturnEmptyList() {
        List<Edge> expectedPath = Collections.emptyList();
        List<Edge> actualPath = dijkstra.findPath(nodeA, nodeA);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testPathFinder_withUnreachableTargetNode_shouldReturnEmptyList() {
        List<Edge> expectedPath = Collections.emptyList();
        List<Edge> actualPath = dijkstra.findPath(nodeA, nodeExcluded);
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testPathFinder_graphShortestPathSizeIsThree() {
        List<Edge> path = dijkstra.findPath(nodeA, nodeE);
        assertEquals(3, path.size());
    }

    @Test
    public void testPathFinder_SourceNodeForEdgeAB_shouldBeToronto() {
        List<Edge> path = dijkstra.findPath(nodeA, nodeB);
        assertEquals("Toronto", path.get(0).getSource().getName());
    }

    @Test
    public void testPathFinder_DestinationNodeForEdgeAB_ShouldBeMontreal() {
        List<Edge> path = dijkstra.findPath(nodeA, nodeB);
        assertEquals("Montreal", path.get(0).getDestination().getName());
    }

    @Test
    public void testPathFinder_graphStartNode_shouldBeToronto() {
        List<Edge> path = dijkstra.findPath(nodeA, nodeE);
        assertEquals("Toronto", path.get(0).getSource().getName());
    }

    @Test
    public void testPathFinder_graphEndNode_shouldBeOttawa() {
        List<Edge> path = dijkstra.findPath(nodeA, nodeE);
        assertEquals("Ottawa", path.get(2).getDestination().getName());
    }
}
