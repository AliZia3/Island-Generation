package ca.mcmaster.cas.se2aa4.a3.island.cities;

import java.util.Random;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Node;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.DirectedGraph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Graph;

public class Cities {
    private Structs.Mesh aMesh;
    private Random random;

    public Cities(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
        random = new Random();
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

        return iMesh.build();
    }

    private boolean isLandPolygon(Structs.Polygon poly) {
        return poly.getProperties(0).getValue() == Properties.landColors ||
               poly.getProperties(0).getValue() == Properties.landLowColors ||
               poly.getProperties(0).getValue() == Properties.landMediumColors ||
               poly.getProperties(0).getValue() == Properties.landHighColors;
    }
}
