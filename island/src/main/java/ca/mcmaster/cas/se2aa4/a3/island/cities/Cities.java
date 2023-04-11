package ca.mcmaster.cas.se2aa4.a3.island.cities;

import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Node;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.DijkstrasAlgorithm;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.PathFinder;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.DirectedGraph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Graph;

public class Cities {
    private Structs.Mesh aMesh;
    private Random random;
    // private Graph graph;
    // private PathFinder<Edge> dijkstra;


    public Cities(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
        random = new Random();
        // this.graph = new DirectedGraph();
        // dijkstra = new DijkstrasAlgorithm();
    }

    public Structs.Mesh generateCities(int numCities) {
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder()
            .addAllVertices(aMesh.getVerticesList())
            .addAllSegments(aMesh.getSegmentsList())
            .addAllPolygons(aMesh.getPolygonsList());

        Graph graph = new DirectedGraph();


        // Adds nodes onto graph
        boolean firstIteration = true;
        for (int i = 0; i < aMesh.getPolygonsCount(); i++) {
            Node nodeType = new Node(0, null, 0);
            int elevation = random.nextInt(1, 100 + 1);
            int randomNodeType = random.nextInt(0, 2 + 1); // Random Node type
            Structs.Polygon poly = aMesh.getPolygons(i); // Get polygon
            int centroid = poly.getCentroidIdx(); // get centroid index of polygon
            Structs.Vertex.Builder c = Structs.Vertex.newBuilder(aMesh.getVertices(centroid)); //create vertex at that centroid location

            if (numCities > 0 && isLandPolygon(poly)) {
                // If first ieration create a capital node
                if (firstIteration) {
                    // Make centroid index id??
                    nodeType = new Node(i, "Capital", elevation);
                    c.addProperties(Properties.getCapitalColorProps()).addProperties(Properties.getCapitalSizeProps());
                    firstIteration = false;
                }
                else {
                    if (randomNodeType == 0) {
                        //can also make it counter instead of i if want more organized (i gives polygon index)
                        nodeType = new Node(i, "City", elevation);
                        c.addProperties(Properties.getCityColorProps()).addProperties(Properties.getCitySizeProps());
                    } 
                    else if (randomNodeType == 1) {
                        nodeType = new Node(i, "Village", elevation);
                        c.addProperties(Properties.getVillagesColorProps()).addProperties(Properties.getVillagesSizeProps());
                    } 
                    else if (randomNodeType == 2) {
                        nodeType = new Node(i, "Hamlet", elevation);
                        c.addProperties(Properties.getHamletsColorProps()).addProperties(Properties.getHamletsSizeProps());
                    } 
                }
                numCities--;
            }
            else if (isLandPolygon(poly)) {
                nodeType = new Node(i, "Road", elevation);
                c.addProperties(Properties.getRoadColorProps()).addProperties(Properties.getRoadSizeProps());
            }
            iMesh.setVertices(centroid, c);
            
            if(nodeType.getName() != null)
                graph.addNode(nodeType);
            // graph.addEdge(new Edge(graph.getNodes().get(i), graph.getNodes().get(i+1), random.nextInt(1, 5+1)));
        }

        List<Node> nodes = graph.getNodes();
        // for (int i = 0; i < nodes.size() - 1; i++) {
        //     Node node1 = nodes.get(i);
        //     Node node2 = nodes.get(i+1);
        //     graph.addEdge(new Edge(node1, node2, random.nextInt(1, 5+1)));
        // }

        System.out.println("==============AFTER ADDDING NODES=============================");
        System.out.println(graph.toString());
        System.out.println(graph.getNodes().get(0).getName());
        System.out.println("========================================================");
        

        // iterate through all centroids that are on the land (cities, roads)
        for(int i = 0; i < nodes.size(); i++) {
            Structs.Polygon poly = aMesh.getPolygons(nodes.get(i).getId()); // get polygon at index i

            // int polyCentroid = poly.getCentroidIdx();
            Node node1 = nodes.get(i); 
            System.out.println("OUTER LOOP POLYGON AT INDEX "+ i +" has ID: " + nodes.get(i).getId() + " And Name: " + nodes.get(i).getName());
            Node node2 = null;
            for (int j=0; j < poly.getNeighborIdxsCount(); j++){
                Structs.Polygon neighborPoly = aMesh.getPolygons(poly.getNeighborIdxs(j));
                if(isLandPolygon(neighborPoly)){
                    // int neighborCentroid = neighborPoly.getCentroidIdx();
                    System.out.println("======================INNER LOOP=====================");
                    node2 = nodes.get(j); //poly.getNeighborIdxs(j) instead of j?
                    System.out.println("INNER LOOP POLYGON AT INDEX "+ j +" has ID: " + nodes.get(j).getId() + " And Name: " + nodes.get(j).getName());
                }
                if (node2 != null){
                    System.out.println("Node Names for Edges: " + node1.getName());
                    System.out.println("Node Names for Edges: " + node2.getName());
                    graph.addEdge(new Edge(node1, node2, random.nextInt(1, 5+1)));
                }
            }
        }

        System.out.println("==============AFTER ADDING EDGES=============================");
        System.out.println(graph.toString());
        System.out.println("========================================================");

        
        System.out.println("===============================PATHFINDING===========================");
        PathFinder<Edge> algorithm = new DijkstrasAlgorithm();
        List<Edge> path = algorithm.findPath(graph.getNodes().get(0), graph.getNodes().get(1), graph);
        

        if (path.isEmpty()) {
            System.out.println("No path found");
        } else {
            double totalWeight = 0;
            System.out.println("Path found:");

            for (Edge edge : path) {
                int destinationID = edge.getDestination().getId();
                int sourceID = edge.getSource().getId();
                String sourceName = edge.getSource().getName();
                String destinationName = edge.getDestination().getName();
                double weight = edge.getWeight();

                System.out.println("(ID:" + sourceID + ") " + sourceName + " -> " + "(ID:" + destinationID + ") " + destinationName + " (" + weight + ")");
                totalWeight += edge.getWeight();

            }
            System.out.println("Shortest Path Total Weight: " + totalWeight);
        }
        

        // for (int i=0; i<numCities; i++) {
        //     Node capital = nodes.get(0);
        //     Node city = nodes.get(i);
            
        //     algorithm = new DijkstrasAlgorithm();
        //     path = algorithm.findPath(graph.getNodes().get(0), graph.getNodes().get(i), graph);
        // }
        
        System.out.println("==================================================================");
        // StarNetwork(graph.getNodes());

        
        
        return iMesh.build();
    }


    // public void StarNetwork(List<Node> nodes){
    //     // Connect capital to every other city
    //     Node capital = nodes.get(0);
    //     // System.out.println(capital.getName());

    //     for (int i = 1; i < nodes.size(); i++) {
    //         Node city = nodes.get(i);
    //         Structs.Vertex capitalCentroid = aMesh.getVertices(aMesh.getPolygons(nodes.get(0).getId()).getCentroidIdx());
    //         Structs.Vertex cityCentroid = aMesh.getVertices(aMesh.getPolygons(nodes.get(i).getId()).getCentroidIdx());
            
    //         graph.addEdge(new Edge(capital, city, random.nextInt(1, 5+1)));
    //         // List<Edge> path = dijkstra.findPath(capital, city, graph);
    //     }

    //     // return iMesh.build();
    // }

    private boolean isLandPolygon(Structs.Polygon poly) {
        return poly.getProperties(0).getValue() == Properties.landColors ||
               poly.getProperties(0).getValue() == Properties.landLowColors ||
               poly.getProperties(0).getValue() == Properties.landMediumColors ||
               poly.getProperties(0).getValue() == Properties.landHighColors;
    }
}
