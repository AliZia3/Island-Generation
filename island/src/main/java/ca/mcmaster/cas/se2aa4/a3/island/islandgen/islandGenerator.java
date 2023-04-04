package ca.mcmaster.cas.se2aa4.a3.island.islandgen;

import java.io.IOException;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Circle;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Square;
import ca.mcmaster.cas.se2aa4.a3.island.shape.TiltedOval;
import ca.mcmaster.cas.se2aa4.a3.island.shape.Shape;
import ca.mcmaster.cas.se2aa4.a3.island.aquifers.Aquifers;
import ca.mcmaster.cas.se2aa4.a3.island.beaches.Beaches;
import ca.mcmaster.cas.se2aa4.a3.island.biomes.Biomes;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.Plains;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.RockMountain;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.Temp;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.Volcano;
import ca.mcmaster.cas.se2aa4.a3.island.Lagoon.Lagoon;
import ca.mcmaster.cas.se2aa4.a3.island.lakes.Lakes;
import ca.mcmaster.cas.se2aa4.a3.island.lakes.Rivers;

public class islandGenerator {
    private Structs.Mesh aMesh;
    public static String biomeArg;

    public islandGenerator() throws IOException {
        aMesh = new MeshFactory().read("img/irregular.mesh");
    }

    public Structs.Mesh generate(Configuration config) throws IOException {
        biomeArg = (config.biomes());
        MeshDimension dim = new MeshDimension(aMesh); // finds mesh dimensions
        Shape iMesh = null;

        if (config.shape().equals("square"))
            iMesh = new Square(dim.maxX, dim.maxY); // Creates square island and passes mesh dimension
        else if (config.shape().equals("circle"))
            iMesh = new Circle(dim.maxX, dim.maxY);
        else if (config.shape().equals("oval"))
            iMesh = new TiltedOval(dim.maxX, dim.maxY);

        if (iMesh == null)
            throw new IllegalArgumentException("Unknown shape: " + config.shape());

        Structs.Mesh mesh = iMesh.build(aMesh); // Calls build function from Shape
        if (config.mode() != null && config.mode().equals("lagoon"))
            mesh = new Lagoon(mesh).build(); // adds lagoon to mesh
        mesh = new Aquifers(mesh).enrichAquifers(config.aquifer()); // adds aquifer
        mesh = new Lakes(mesh).generateLakes(Integer.parseInt(config.lakes()));
        mesh = new Rivers(mesh).generateRivers(7);
        mesh = new Volcano().build(mesh);
        mesh = new RockMountain().build(mesh);
        mesh = new Beaches(mesh).enrichBeaches();
        mesh = new Plains().addElevation(mesh); // index 3?
        mesh = new Temp(mesh).enrichTemp(); // index 4
        mesh = new Biomes(mesh).enrichBiomes();

        // Seed generation
        SeedGen seedGen = new SeedGen();
        long seed = (config.seed() == null) ? (System.currentTimeMillis()) : Long.parseLong(config.seed());
        if (config.seed() == null)
            seedGen.saveMesh(seed, mesh);
        mesh = seedGen.getMesh(seed);
        System.out.println("SEED: " + seed);

        return mesh; // returns the mesh
    }
}