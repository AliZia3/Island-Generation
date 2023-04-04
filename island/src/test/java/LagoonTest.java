import org.junit.Before;
import org.junit.Test;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Properties.Properties;
import ca.mcmaster.cas.se2aa4.a3.island.Lagoon.Lagoon;

import static org.junit.Assert.*;

public class LagoonTest {
    private Mesh MESH;

    @Before
    public void setUp() {
        MESH = Mesh.newBuilder()
                .addVertices(Vertex.newBuilder().setX(0).setY(0).build())
                .addVertices(Vertex.newBuilder().setX(1).setY(0).build())
                .addVertices(Vertex.newBuilder().setX(2).setY(0).build())
                .addVertices(Vertex.newBuilder().setX(3).setY(0).build())
                .build();
    }

    @Test
    public void testConstructor() {
        Lagoon lagoon = new Lagoon(MESH);
        assertEquals(lagoon.build().getVerticesList(), MESH.getVerticesList());
        assertEquals(lagoon.build().getSegmentsList(), MESH.getSegmentsList());
    }

    @Test
    public void testCalculateRadius() {
        Lagoon lagoon = new Lagoon(MESH);
        double expected = 0.6000000000000001;
        double actual = lagoon.calculateRadius(3, 4);
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void testIsWithinLagoon() {
        Lagoon lagoon = new Lagoon(MESH);
        assertFalse(lagoon.isWithinLagoon(2.5, 2.5));
    }
}