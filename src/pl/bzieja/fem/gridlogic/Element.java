package pl.bzieja.fem.gridlogic;

import pl.bzieja.fem.gridlogic.Node;

public class Element {
    private Node[] ID; // Nodes included in finite Element
    private double[][] matrixH;
    private double[][] matrixC;

    /*
     *   ID4         ID3
     *   -------------
     *   |           |
     *   |           |
     *   -------------
     *   ID1          ID2
     * */

    public Element(Node ID1, Node ID2, Node ID3, Node ID4) {
        this.ID = new Node[] {ID1, ID2, ID3, ID4};
        this.matrixH = new double[4][4];
    }

    public Node[] getID() {
        return ID;
    }

    public void setMatrixH(double[][] matrixH) {
        this.matrixH = matrixH;
    }

    public double[][] getMatrixH() {
        return matrixH;
    }

    public void setMatrixC(double[][] matrixC) {
        this.matrixC = matrixC;
    }

    public double[][] getMatrixC() {
        return matrixC;
    }
}
