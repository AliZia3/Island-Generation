import java.io.IOException;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.SeedGen;
import ca.mcmaster.cas.se2aa4.a3.island.islandGenerator;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

public class Main {
    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Structs.Mesh iMesh = new islandGenerator().generate(config);
        new MeshFactory().write(iMesh, config.output());
    }
}