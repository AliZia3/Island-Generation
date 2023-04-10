package ca.mcmaster.cas.se2aa4.a3.island.cities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.protobuf.Struct;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Node;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.DirectedGraph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.adt.Graph;
import java.awt.geom.Ellipse2D;

public class Cities {
    private Structs.Mesh aMesh;
    private Random random;


    public Cities(Structs.Mesh iMesh) {
        this.aMesh = iMesh;
        random = new Random();
    }

    public Structs.Mesh generateCities(int numCities) {
        Graph graph = new DirectedGraph();
        Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
        iMesh.addAllVertices(aMesh.getVerticesList());
        iMesh.addAllSegments(aMesh.getSegmentsList());
        iMesh.addAllPolygons(aMesh.getPolygonsList());


        int counter = 0;
        for (int i = 0; i < aMesh.getPolygonsCount(); i++) {
            if (numCities > 0 && (aMesh.getPolygons(i).getProperties(0).getValue() == Properties.landColors || aMesh.getPolygons(i).getProperties(0).getValue() == Properties.landLowColors || aMesh.getPolygons(i).getProperties(0).getValue() == Properties.landMediumColors)) {
                int elevation = random.nextInt(1, 100 + 1); // Give random elevation
                int randomNodeType = random.nextInt(0, 2 + 1); // Random Node type
                Node nodeType = new Node(0, null, 0); // Initialize node object

                Structs.Polygon poly = aMesh.getPolygons(i); // Get polygon
                int centroid = poly.getCentroidIdx(); // get centroid index of polygon
                Structs.Vertex.Builder c = Structs.Vertex.newBuilder(aMesh.getVertices(centroid));

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
                graph.addNode(nodeType);
                numCities--;
            }
        }
        System.out.println(graph.toString());

        
        return iMesh.build();
    }

}
