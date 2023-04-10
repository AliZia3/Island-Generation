// package ca.mcmaster.cas.se2aa4.a3.island.cities;

// import java.util.ArrayList;
// import java.util.Random;

// import ca.mcmaster.cas.se2aa4.a2.io.Structs;
// import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
// import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
// import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;

// public class City {
    

// }




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

//     // Take CLA and produce correct number of cities and assign them a color
//     public Structs.Mesh enrichCities(int numCities){
//         Structs.Mesh.Builder iMesh = Structs.Mesh.newBuilder();
//         iMesh.addAllVertices(aMesh.getVerticesList());
//         iMesh.addAllSegments(aMesh.getSegmentsList());

//         int randomPolyIdx = random.nextInt(aMesh.getPolygonsCount());

//     }

// }
