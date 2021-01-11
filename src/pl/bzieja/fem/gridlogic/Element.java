package pl.bzieja.fem.gridlogic;

import pl.bzieja.fem.gridlogic.Node;

public class Element {
    private Node[] ID; // Nodes included in finite Element
    private double[][] matrixH; //(H + HBC)
    private double[][] matrixC;
    private double[][] matrixBC;    //for debug, can be omitted
    private double[][] vectorP;

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
        this.vectorP = new double[1][4];
    }

    public boolean isBoundaryElement() {
        int counter = 0;

        for (int i = 0; i < this.ID.length; i++) {
            if (this.ID[i].isBoundaryCondition()) {
                counter++;
            }
        }

        if (counter >= 2) {
            return true;
        } else {
            return false;
        }

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

    public double[][] getMatrixBC() {
        return matrixBC;
    }

    public void setMatrixBC(double[][] matrixBC) {
        this.matrixBC = matrixBC;
    }

    public double[][] getVectorP() {
        return vectorP;
    }

    public void setVectorP(double[][] vectorP) {
        this.vectorP = vectorP;
    }
}
