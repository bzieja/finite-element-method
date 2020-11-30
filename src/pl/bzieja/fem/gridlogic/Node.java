package pl.bzieja.fem.gridlogic;

public class Node {
    static private int nextNodeNumber = 1;

    private double x;
    private double y;
    private final int nodeID;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
        this.nodeID = nextNodeNumber;
        nextNodeNumber++;
    }

    public int getNodeID() {
        return nodeID;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
