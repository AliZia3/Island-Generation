// package ca.mcmaster.cas.se2aa4.a3.island.cities;

// import java.util.Random;

// import ca.mcmaster.cas.se2aa4.a2.io.Structs;
// import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.DirectedGraph;
// import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Edge;
// import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Graph;
// import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Node;

// public class MeshToGraph {
//     private Structs.Mesh aMesh;
//     private Random random;

//     public MeshToGraph(Structs.Mesh mesh) {
//         this.aMesh = mesh;
//         random = new Random();
//     }

//     public Graph convert() {
//         Graph graph = new DirectedGraph();

//         // Adding all centroids to graph to represent the nodes
//         for (Structs.Polygon p : aMesh.getPolygonsList()) {
//             Node node = new Node(p.getCentroidIdx(), "node", random.nextInt(1, 100 + 1));
//             graph.addNode(node);
//         }

//         // Connecting all neighboring centroids with graph edges
//         for (Structs.Polygon p : aMesh.getPolygonsList()) {
//             Node node1 = new Node(p.getCentroidIdx(), "node", random.nextInt(1, 5 + 1));
//             for (int idx : p.getNeighborIdxsList()) {
//                 Node node2 = new Node(aMesh.getPolygons(idx).getCentroidIdx(), "node", random.nextInt(0, 100 + 1));
//                 graph.addEdge(new Edge(node1, node2, random.nextInt(1, 5 + 1)));
//             }
//         }
//         return graph;

//     }
// }
