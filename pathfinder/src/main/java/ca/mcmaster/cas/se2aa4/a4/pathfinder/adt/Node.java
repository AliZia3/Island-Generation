package ca.mcmaster.cas.se2aa4.a4.pathfinder.adt;

public class Node {
    private int id;
    private String name;
    private int elevation;

    public Node(int id, String name, int elevation) {
        this.id = id;
        this.name = name;
        this.elevation = elevation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getElevation() {
        return elevation;
    }
}
