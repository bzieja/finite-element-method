package pl.bzieja.fem;

public class SOE {
    //H_GLOBAL [n x n], where n = nH * nW
    //C_GLOBAL [n x n]
    //P_GLOBAL [1 x n]

    double[][] matrixHGlobal;

    Grid grid;
    GlobalData globalData;
    UniversalElement universalElement;

    public SOE(Grid grid, GlobalData globalData, UniversalElement universalElement) {
        this.grid = grid;
        this.globalData = globalData;
        this.universalElement = universalElement;
        this.matrixHGlobal = new double[globalData.getNumberOfNodes()][globalData.getNumberOfNodes()];

        calculateMatrixHGlobal();
    }

    private void calculateMatrixHGlobal(){

        for (int i = 0; i < globalData.getNumberOfElements(); i++) {
            Jacobian jacobian = new Jacobian(grid.getElements()[i], universalElement);
            MatrixH matrixH = new MatrixH(jacobian, universalElement, globalData.getK());
            grid.getElements()[i].setMatrixH(matrixH.getLocalMatrixH());
        }

        for (int k = 0; k < globalData.getNumberOfElements(); k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    matrixHGlobal[grid.getElements()[k].getID()[i].getNodeID() - 1][grid.getElements()[k].getID()[j].getNodeID() - 1] += grid.getElements()[k].getMatrixH()[i][j];
                }
            }
        }
    }

    public void printMatrixHGlobal() {
        for (int i = 0; i < globalData.getNumberOfNodes(); i++) {
            for (int j = 0; j < globalData.getNumberOfNodes(); j++) {
                System.out.printf("%.4f\t\t", matrixHGlobal[i][j]);
            }
            System.out.print("\n");
        }
    }



}
