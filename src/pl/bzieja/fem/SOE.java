package pl.bzieja.fem;

import pl.bzieja.fem.gridlogic.Grid;
import pl.bzieja.fem.mathlogic.MatrixOperations;

public class SOE {
    //H_GLOBAL [n x n], where n = nH * nW
    //C_GLOBAL [n x n]
    //P_GLOBAL [1 x n]

    double[][] matrixHGlobal;
    double[][] matrixCGlobal;

    Grid grid;
    GlobalData globalData;
    UniversalElement universalElement;

    public SOE(Grid grid, GlobalData globalData, UniversalElement universalElement) {
        this.grid = grid;
        this.globalData = globalData;
        this.universalElement = universalElement;
        this.matrixHGlobal = new double[globalData.getNumberOfNodes()][globalData.getNumberOfNodes()];
        this.matrixCGlobal = new double[globalData.getNumberOfNodes()][globalData.getNumberOfNodes()];

        calculateMatrixHGlobal();
        calculateMatrixCGlobal();
    }

    private void calculateMatrixHGlobal(){

        //calculate Hbc for each element
        for (int i = 0; i < globalData.getNumberOfElements(); i++) {
            Jacobian jacobian = new Jacobian(grid.getElements()[i], universalElement);
            MatrixH matrixH = new MatrixH(jacobian, universalElement, globalData.getK());
            grid.getElements()[i].setMatrixH(matrixH.getLocalMatrixH());

            //check if Element has BC and if yes - count BC Matrix
            if (grid.getElements()[i].isBoundaryElement()) {
                MatrixBoundaryConditions matrixBoundaryConditions = new MatrixBoundaryConditions(grid.getElements()[i], universalElement, globalData.getAlfa());
                grid.getElements()[i].setMatrixBC(matrixBoundaryConditions.getBCMatrix());
                grid.getElements()[i].setMatrixH(MatrixOperations.sumMatrices(matrixH.getLocalMatrixH(), grid.getElements()[i].getMatrixBC()));
            }

        }

        //aggregation
        for (int k = 0; k < globalData.getNumberOfElements(); k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    matrixHGlobal[grid.getElements()[k].getID()[i].getNodeID() - 1][grid.getElements()[k].getID()[j].getNodeID() - 1] += grid.getElements()[k].getMatrixH()[i][j];
                }
            }
        }
    }

    private void calculateMatrixCGlobal(){

        //calculate H for each element
        for (int i = 0; i < globalData.getNumberOfElements(); i++) {
            Jacobian jacobian = new Jacobian(grid.getElements()[i], universalElement);
            MatrixC matrixC = new MatrixC(jacobian, universalElement, globalData.getC(), globalData.getRo());
            grid.getElements()[i].setMatrixC(matrixC.getLocalMatrixC());
        }

        //aggregation
        for (int k = 0; k < globalData.getNumberOfElements(); k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    matrixCGlobal[grid.getElements()[k].getID()[i].getNodeID() - 1][grid.getElements()[k].getID()[j].getNodeID() - 1] += grid.getElements()[k].getMatrixC()[i][j];
                }
            }
        }

    }

    public void printMatrixHGlobal() {
        System.out.println("Matrix H Global:");
        for (int i = 0; i < globalData.getNumberOfNodes(); i++) {
            for (int j = 0; j < globalData.getNumberOfNodes(); j++) {
                System.out.printf("%.4f\t\t", matrixHGlobal[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public void printMatrixCGlobal() {
        System.out.println("Matrix C Global:");
        for (int i = 0; i < globalData.getNumberOfNodes(); i++) {
            for (int j = 0; j < globalData.getNumberOfNodes(); j++) {
                System.out.printf("%.3f\t\t", matrixCGlobal[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    } //print for debug

}
