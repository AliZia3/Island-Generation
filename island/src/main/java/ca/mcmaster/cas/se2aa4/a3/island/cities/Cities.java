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
    private Graph graph;
    private PathFinder<Edge> dijkstra;


    public Cities(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
        random = new Random();
        this.graph = new DirectedGraph();
        dijkstra = new DijkstrasAlgorithm();
    }

    public Structs.Mesh generateCities(int numCities) {
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder()
            .addAllVertices(aMesh.getVerticesList())
            .addAllSegments(aMesh.getSegmentsList())
            .addAllPolygons(aMesh.getPolygonsList());


        // Adds nodes onto graph
        int counter = 0;
        for (int i = 0; i < aMesh.getPolygonsCount(); i++) {
            Structs.Polygon poly = aMesh.getPolygons(i); // Get polygon
            Node nodeType = new Node(0, null, 0);
            int elevation = random.nextInt(1, 100 + 1);
            
            if (numCities > 0 && isLandPolygon(poly)) {
                int randomNodeType = random.nextInt(0, 2 + 1); // Random Node type

                // Structs.Polygon poly = aMesh.getPolygons(i); // Get polygon
                int centroid = poly.getCentroidIdx(); // get centroid index of polygon
                Structs.Vertex.Builder c = Structs.Vertex.newBuilder(aMesh.getVertices(centroid)); //create vertex at that centroid location

                // If first ieration create a capital node
                if (counter == 0) {
                    // Make centroid index id??
                    nodeType = new Node(i, "Capital", elevation);
                    c.addProperties(Properties.getCapitalColorProps()).addProperties(Properties.getCapitalSizeProps());
                    counter++;
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
                iMesh.setVertices(centroid, c);
                numCities--;
            }
            else if (isLandPolygon(poly)) {
                int centroid = poly.getCentroidIdx();
                Structs.Vertex.Builder c = Structs.Vertex.newBuilder(aMesh.getVertices(centroid));
                nodeType = new Node(i, "Road", elevation);
                c.addProperties(Properties.getRoadColorProps()).addProperties(Properties.getRoadSizeProps());
                iMesh.setVertices(centroid, c);
            }
            graph.addNode(nodeType);
        }

        System.out.println(graph.toString());
        System.out.println("==================================================================");
        // StarNetwork(graph.getNodes());

        
        return iMesh.build();
    }


    public void StarNetwork(List<Node> nodes){
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());
        iMesh.addAllPolygons(aMesh.getPolygonsList());

        // Connect capital to every other city
        Node capital = nodes.get(0);
        // System.out.println(capital.getName());

        for (int i = 1; i < nodes.size(); i++) {
            Node city = nodes.get(i);
            Structs.Vertex capitalCentroid = aMesh.getVertices(aMesh.getPolygons(nodes.get(0).getId()).getCentroidIdx());
            Structs.Vertex cityCentroid = aMesh.getVertices(aMesh.getPolygons(nodes.get(i).getId()).getCentroidIdx());
            
            graph.addEdge(new Edge(capital, city, random.nextInt(1, 5+1)));
            // List<Edge> path = dijkstra.findPath(capital, city, graph);
        }

        // return iMesh.build();
    }

    private boolean isLandPolygon(Structs.Polygon poly) {
        return poly.getProperties(0).getValue() == Properties.landColors ||
               poly.getProperties(0).getValue() == Properties.landLowColors ||
               poly.getProperties(0).getValue() == Properties.landMediumColors ||
               poly.getProperties(0).getValue() == Properties.landHighColors;
    }
}
