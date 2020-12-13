package pl.bzieja.fem.gridlogic;

import pl.bzieja.fem.GlobalData;

public class Node {
    static private int nextNodeNumber = 1;

    private double x;
    private double y;
    private final int nodeID;
    boolean isBoundaryCondition;

    public Node(double x, double y, boolean isBoundaryCondition) {
        this.x = x;
        this.y = y;
        this.isBoundaryCondition = isBoundaryCondition;
        this.nodeID = nextNodeNumber;
        nextNodeNumber++;
    }

    public boolean isBoundaryCondition() {
        return isBoundaryCondition;
    }

    public static boolean isBoundaryNode(double x, double y, GlobalData globalData) {
        boolean isBoundary = false;

        if (x == 0 || y == 0) {
            isBoundary = true;
        } else if(y == globalData.getHeight() || x == globalData.getWidth()) {
            isBoundary = true;
        }

        return isBoundary;
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
