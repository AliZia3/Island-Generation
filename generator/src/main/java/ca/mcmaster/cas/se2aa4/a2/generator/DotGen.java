package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    private final int precision = 2;

    MeshADT mesh = new MeshADT(precision);

    public Mesh generate() {
        // ArrayList<Vertex> vertices = new ArrayList<>();
        // Create all the vertices
        for (int x = 0; x < width; x += square_size) {
            for (int y = 0; y < height; y += square_size) {
                mesh.addVertex(x, y);
                mesh.addVertex(x + square_size, y);
                mesh.addVertex(x, y + square_size);
                mesh.addVertex(x + square_size, y + square_size);
            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> verticesWithProps = new ArrayList<>();
        Random bag = new Random();
        for (Vertex v : mesh.getVertexs()) {
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);

            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            String thicknessVal = String.valueOf(bag.nextInt(8) + 1);
            Property thickness = Property.newBuilder().setKey("thickness").setValue(thicknessVal).build();
            Vertex verticesProps = Vertex.newBuilder(v).addProperties(color).addProperties(thickness).build();
            verticesWithProps.add(verticesProps);
        }

        // Create all the edges
        for (int i = 0; i < mesh.getVertexs().size() - 2; i += 4) {
            mesh.addSegment(i, i + 1);
            mesh.addSegment(i + 1, i + 3);
            mesh.addSegment(i + 3, i + 2);
            mesh.addSegment(i + 2, i);
        }

        // Distribute the average of each color randomly and also give the segments
        // thickness values
        ArrayList<Segment> edgesWithProps = new ArrayList<>();
        for (Segment e : mesh.getSegments()) {
            int avgRed;
            int avgGreen;
            int avgBlue;

            Vertex v1 = verticesWithProps.get(e.getV1Idx());
            Vertex v2 = verticesWithProps.get(e.getV2Idx());
            String v1_colorCode = "";
            String v2_colorCode = "";

            // List stores the properties of the vertices
            List<Property> v1_PropsList = v1.getPropertiesList();
            List<Property> v2_PropsList = v2.getPropertiesList();

            // Extracts the color props
            for (int i = 0; i < v1_PropsList.size(); i++) {
                if (v1_PropsList.get(i).getKey().equals("rgb_color")) {
                    v1_colorCode = v1_PropsList.get(i).getValue();
                }
            }
            for (int i = 0; i < v2_PropsList.size(); i++) {
                if (v2_PropsList.get(i).getKey().equals("rgb_color")) {
                    v2_colorCode = v2_PropsList.get(i).getValue();
                }
            }

            // Extracting the colors
            String[] v1_colors = v1_colorCode.split(",");
            String[] v2_colors = v2_colorCode.split(",");

            int v1_red = Integer.parseInt(v1_colors[0]);
            int v1_green = Integer.parseInt(v1_colors[1]);
            int v1_blue = Integer.parseInt(v1_colors[2]);
            int v2_red = Integer.parseInt(v2_colors[0]);
            int v2_green = Integer.parseInt(v2_colors[1]);
            int v2_blue = Integer.parseInt(v2_colors[2]);

            avgRed = (v1_red + v2_red) / 2;
            avgGreen = (v1_green + v2_green) / 2;
            avgBlue = (v1_blue + v2_blue) / 2;

            String colorCode = avgRed + "," + avgGreen + "," + avgBlue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            String thicknessVal = String.valueOf(bag.nextInt(5) + 1);
            Property thickness = Property.newBuilder().setKey("thickness").setValue(thicknessVal).build();
            Segment segmentProps = Segment.newBuilder(e).addProperties(color).addProperties(thickness).build();

            edgesWithProps.add(segmentProps);
        }

        // create polygons
        for (int i = 0; i < mesh.getSegments().size() - 1; i += 4) {
            mesh.addPolygon(i, i + 1, i + 2, i + 3);
        }

        ArrayList<Vertex> centroids = new ArrayList<>();
        for (int i = 0; i < width; i += square_size) {
            for (int j = 0; j < height; j += square_size) {
                double x = i + (square_size / 2);
                double y = j + (square_size / 2);

                centroids.add(Vertex.newBuilder().setX(x).setY(y).build());
                // Polygon.newBuilder().setCentroidIdx(centroids.size());
            }
        }

        ArrayList<Vertex> centroidsWithProps = new ArrayList<>();
        for (Vertex c : centroids) {
            String colorCode = 255 + "," + 0 + "," + 0;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex centroidProps = Vertex.newBuilder(c).addProperties(color).build();
            verticesWithProps.add(centroidProps);
        }

        return Mesh.newBuilder().addAllVertices(verticesWithProps).addAllSegments(edgesWithProps)
                .addAllVertices(centroidsWithProps).addAllPolygons(mesh.getPolygons()).build();
    }

}