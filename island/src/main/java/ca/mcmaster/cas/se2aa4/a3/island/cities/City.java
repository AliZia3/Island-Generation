// package ca.mcmaster.cas.se2aa4.a3.island.cities;

// import java.util.ArrayList;
// import java.util.Random;

// import ca.mcmaster.cas.se2aa4.a2.io.Structs;
// import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
// import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
// import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;

// public class City {
//     private Structs.Mesh aMesh;
//     private Random random;
//     private Structs.Property hamlets;
//     private Structs.Property villages;
//     private Structs.Property cities;
//     private Structs.Property capital;

//     public City(Structs.Mesh iMesh) {
//         this.aMesh = iMesh;
//         random = new Random();
//         hamlets = Properties.getHamletsProps();
//         villages = Properties.getVillagesProps();
//         cities = Properties.getCitiesProps();
//         capital = Properties.getCapitalProps();
//     }

//     public Structs.Mesh enrichCities(int numCities){
//         Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
//         iMesh.addAllVertices(aMesh.getVerticesList());
//         iMesh.addAllSegments(aMesh.getSegmentsList());

//         int randomPolyIdx = random.nextInt(aMesh.getPolygonsCount());

//         while(numCities>0) {
//             if (aMesh.getPolygons(randomPolyIdx).getProperties(0).getValue() == Properties.landColors) {
//                 Structs.Polygon randomPoly = aMesh.getPolygons(randomPolyIdx); // Get polygon at the random index
//                 Structs.Polygon.Builder p = Structs.Polygon.newBuilder(randomPoly);
//                 Structs.Vertex centroid = aMesh.getVertices(p.getCentroidIdx());
//                 centroid.setProperties(0,hamlets);
//                 numCities--;
//                 iMesh.setPolygons(randomPolyIdx, p);
//             }
//             randomPolyIdx = random.nextInt(aMesh.getPolygonsCount());
//         }




//          // Distribute colors randomly. Vertices are immutable, need to enrich them
//          ArrayList<Vertex> verticesWithProps = new ArrayList<>();
//          Random bag = new Random();
//          for (Vertex v : mesh.getVertexs()) {
//              int red = bag.nextInt(255);
//              int green = bag.nextInt(255);
//              int blue = bag.nextInt(255);
 
//              String colorCode = red + "," + green + "," + blue;
//              Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
//              String thicknessVal = String.valueOf(bag.nextInt(8) + 1);
//              Property thickness = Property.newBuilder().setKey("thickness").setValue(thicknessVal).build();
//              Vertex verticesProps = Vertex.newBuilder(v).addProperties(color).addProperties(thickness).build();
//              verticesWithProps.add(verticesProps);
//          }
         
//         ArrayList<Vertex> centroidsWithProps = new ArrayList<>();
//         for (Vertex c : centroids) {
//             String colorCode = 255 + "," + 0 + "," + 0;
//             Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
//             Vertex centroidProps = Vertex.newBuilder(c).addProperties(color).build();
//             verticesWithProps.add(centroidProps);
//         }
//     }

// }
