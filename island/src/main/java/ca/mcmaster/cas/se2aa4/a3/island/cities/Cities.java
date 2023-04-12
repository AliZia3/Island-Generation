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
        boolean isFirstIteration = true;

        for (int i = 0; i < aMesh.getPolygonsCount(); i++) {
            Structs.Polygon poly = aMesh.getPolygons(i);
            int centroidIdx = poly.getCentroidIdx();
            int elevation = random.nextInt(1, 100 + 1);
            int nodeType = -1;

            if (numCities > 0 && isLandPolygon(poly)) {
                nodeType = isFirstIteration ? 0 : random.nextInt(1, 3);
                isFirstIteration = false;
                numCities--;
            } else if (isLandPolygon(poly)) {
                // Road
                nodeType = 4;
            }

            if (nodeType != -1) {
                String name = null;
                Structs.Vertex.Builder centroidVertex = Structs.Vertex.newBuilder(aMesh.getVertices(centroidIdx))
                        .addProperties(Properties.getCitiesColorProps(nodeType))
                        .addProperties(Properties.getCitiesSizeProps(nodeType));

                if (nodeType == 0) name = "Capital";
                else if (nodeType == 1) name = "City";
                else if (nodeType == 2) name = "Village";
                else if (nodeType == 3) name = "Hamlet";
                else if (nodeType == 4) name = "Road";
                

                Node node = new Node(centroidIdx, name, elevation);
                graph.addNode(node);
                iMesh.setVertices(centroidIdx, centroidVertex);
            }
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
