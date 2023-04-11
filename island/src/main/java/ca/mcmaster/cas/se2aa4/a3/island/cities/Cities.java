package ca.mcmaster.cas.se2aa4.a3.island.cities;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
        Graph neighborGraph = new DirectedGraph();


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
                    nodeType = new Node(centroid, "Capital", elevation);
                    c.addProperties(Properties.getCapitalColorProps()).addProperties(Properties.getCapitalSizeProps());
                    firstIteration = false;
                }
                else {
                    if (randomNodeType == 0) {
                        //can also make it counter instead of i if want more organized (i gives polygon index)
                        nodeType = new Node(centroid, "City", elevation);
                        c.addProperties(Properties.getCityColorProps()).addProperties(Properties.getCitySizeProps());
                    } 
                    else if (randomNodeType == 1) {
                        nodeType = new Node(centroid, "Village", elevation);
                        c.addProperties(Properties.getVillagesColorProps()).addProperties(Properties.getVillagesSizeProps());
                    } 
                    else if (randomNodeType == 2) {
                        nodeType = new Node(centroid, "Hamlet", elevation);
                        c.addProperties(Properties.getHamletsColorProps()).addProperties(Properties.getHamletsSizeProps());
                    } 
                }
                numCities--;
            }
            else if (isLandPolygon(poly)) {
                nodeType = new Node(centroid, "Road", elevation);
                c.addProperties(Properties.getRoadColorProps()).addProperties(Properties.getRoadSizeProps());
            }
            iMesh.setVertices(centroid, c);
            
            if(nodeType.getName() != null)
                graph.addNode(nodeType);
        }


        List<Node> nodes = graph.getNodes();
        // Set<Set<Structs.Polygon>> drawn = new HashSet<>();
        // Node node1 = null;
        // Node node2 = null;
        // for(Structs.Polygon p: aMesh.getPolygonsList()){
        //     if(isLandPolygon(p)){
        //         int centroidIdx = p.getCentroidIdx();
                
        //         // Structs.Vertex centroid = aMesh.getVertices(centroidIdx);
                
        //         // int currentCentroidId = nodes.get(centroidIdx).getId();
        //         // String currentCentroidName = nodes.get(centroidIdx).getName();
        //         int elevation = random.nextInt(1, 100+1);
                
        //         node1 = new Node(centroidIdx, "Node1", elevation);
        //         neighborGraph.addNode(node1);
                
        //         for(Integer neigbourIdx: p.getNeighborIdxsList()){
        //             Structs.Polygon neighbour = aMesh.getPolygons(neigbourIdx);
        //             if(isLandPolygon(neighbour) && !drawn.contains(Set.of(p, neighbour))){
        //                 int neighborCentroidIdx = neighbour.getCentroidIdx();
        //                 // Structs.Vertex neighbourCentroid = aMesh.getVertices(neighborCentroidIdx);
                        
        //                 // int neighborCentroidId = nodes.get(neighborCentroidIdx).getId();
        //                 // String neighborCentroidName = nodes.get(neighborCentroidIdx).getName();

        //                 node2 = new Node(neighborCentroidIdx, "Node2", elevation);
        //                 drawn.add(Set.of(p, neighbour));
        //                 neighborGraph.addNode(node2);    
        //                 neighborGraph.addEdge(new Edge(node1, node2, random.nextInt(1, 5+1)));
        //             }
        //         }
        //     }
        // }

        Set<Set<Structs.Polygon>> drawn = new HashSet<>();
        for(int i=0; i<nodes.size(); i++) {
            Structs.Polygon p = aMesh.getPolygons(i);
            Node node1 = null;
            Node node2 = null;

            node1 = nodes.get(i);
            neighborGraph.addNode(node1);

            for(int j=0; j<p.getNeighborIdxsCount(); j++){
                Structs.Polygon neighbour = aMesh.getPolygons(p.getNeighborIdxs(j));
                int neighborCentroidIdx = neighbour.getCentroidIdx();
                
                if(isLandPolygon(neighbour) && !drawn.contains(Set.of(p, neighbour))){
                    // get centroid of neighbor poly
                    
                    for(int k=0; k<nodes.size(); k++) {
                        if(nodes.get(k).getId() == neighborCentroidIdx){
                            node2 = nodes.get(k);
                        }
                    }
                    // node2 = nodes.get(p.getNeighborIdxs(j));
                    drawn.add(Set.of(p, neighbour));
                    neighborGraph.addNode(node2);
                    neighborGraph.addEdge(new Edge(node1, node2, random.nextInt(1, 5+1)));
                }
            }
        }



        
        // for(int i = 0; i < nodes.size(); i++) {
        //     Structs.Polygon poly = aMesh.getPolygons(nodes.get(i).getId());

        //     Node node1 = nodes.get(i); 
        //     Node node2 = null;
        //     for (int j=0; j < poly.getNeighborIdxsCount(); j++){
        //         Structs.Polygon neighborPoly = aMesh.getPolygons(poly.getNeighborIdxs(j));
        //         if(isLandPolygon(neighborPoly)){
        //             node2 = nodes.get(j);
        //         }
        //         if (node2 != null){
        //             graph.addEdge(new Edge(node1, node2, random.nextInt(1, 5+1)));
        //         }
        //     }
        // }


        
        System.out.println("===============================PATHFINDING===========================");
        PathFinder<Edge> algorithm = new DijkstrasAlgorithm();
        List<Edge> path = algorithm.findPath(neighborGraph.getNodes().get(0), neighborGraph.getNodes().get(6), neighborGraph);

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
        
        System.out.println("==================================================================");

        
        
        return iMesh.build();
    }

    private boolean isLandPolygon(Structs.Polygon poly) {
        return poly.getProperties(0).getValue() == Properties.landColors ||
               poly.getProperties(0).getValue() == Properties.landLowColors ||
               poly.getProperties(0).getValue() == Properties.landMediumColors ||
               poly.getProperties(0).getValue() == Properties.landHighColors;
    }
}
