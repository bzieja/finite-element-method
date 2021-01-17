package pl.bzieja.fem;

import pl.bzieja.fem.applogic.*;
import pl.bzieja.fem.gridlogic.Grid;
import pl.bzieja.fem.mathlogic.EquationsSolver;
import pl.bzieja.fem.mathlogic.MatrixOperations;

import java.util.*;
import java.util.stream.Collectors;

public class SOE {
    //H_GLOBAL [n x n], where n = nH * nW
    //C_GLOBAL [n x n]
    //P_GLOBAL [1 x n]

    double[][] matrixHGlobal;
    double[][] matrixCGlobal;
    double[][] vectorPGlobal;

    Grid grid;
    GlobalData globalData;
    UniversalElement universalElement;

    public SOE(Grid grid, GlobalData globalData) {
        this.grid = grid;
        this.globalData = globalData;
        this.universalElement = new UniversalElement(globalData.getNumberOfIntegrationPoints());
        this.matrixHGlobal = new double[globalData.getNumberOfNodes()][globalData.getNumberOfNodes()];
        this.matrixCGlobal = new double[globalData.getNumberOfNodes()][globalData.getNumberOfNodes()];
        this.vectorPGlobal = new double[1][globalData.getNumberOfNodes()];
    }

    public void startSimulation() {

        double[][] t0 = globalData.getInitTemperature();

        for (double elapsedTime = 0.0; elapsedTime < globalData.getSimulationTime(); elapsedTime += globalData.getSimulationStep()) {
            calculateMatrixHGlobal();
            calculateMatrixCGlobal();
            calculateVectorPGlobal();

            //zastepcza macierz H
            double[][] H_ = MatrixOperations.sumMatrices(matrixHGlobal, MatrixOperations.multiplyMatrixByConstant(matrixCGlobal,
                    1.0 / globalData.getSimulationStep()));

            //zastepczy wektor P
            double[][] P_ = MatrixOperations.multiplyMatrices(t0, MatrixOperations.multiplyMatrixByConstant(matrixCGlobal,
                    -1.0 / globalData.getSimulationStep()));
            P_ = MatrixOperations.sumMatrices(P_, vectorPGlobal);
            P_ = MatrixOperations.multiplyMatrixByConstant(P_, -1.0);

            EquationsSolver equationsSolver = new EquationsSolver(H_, P_);
            t0 = equationsSolver.getX();


            DoubleSummaryStatistics stats = Arrays.stream(t0).flatMapToDouble(Arrays::stream)
                    .boxed().collect(Collectors.summarizingDouble(Double::doubleValue));
            double maxTemp = stats.getMax();
            double minTemp = stats.getMin();

            //System.out.println("Time: " + (elapsedTime + globalData.getSimulationStep()) + "\tMinTemp: " + minTemp + "\tMaxTemp: " + maxTemp);
            System.out.println((elapsedTime + globalData.getSimulationStep()) + "," + minTemp + "," + maxTemp);
        }
    }

    private void calculateMatrixHGlobal() {

        //calculate Hlbc for each element
        for (int i = 0; i < globalData.getNumberOfElements(); i++) {
            Jacobian jacobian = new Jacobian(grid.getElements()[i], universalElement);
            MatrixH matrixH = new MatrixH(jacobian, universalElement, globalData.getK());
            grid.getElements()[i].setMatrixH(matrixH.getLocalMatrixH());

            //check if Element has BC and if yes - calculate BC Matrix
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

    private void calculateMatrixCGlobal() {

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

    private void calculateVectorPGlobal() {

        //calculate VectorP for boundary elements
        for (int i = 0; i < globalData.getNumberOfElements(); i++) {

            //check if Element has BC and if yes - count BC Matrix
            if (grid.getElements()[i].isBoundaryElement()) {
                VectorP vectorP = new VectorP(grid.getElements()[i], universalElement, globalData.getAlfa(), globalData.gettAlfa());

                grid.getElements()[i].setVectorP(vectorP.getVectorP());

                //System.out.println("Element " + i + "ma vector P: " + Arrays.deepToString(grid.getElements()[i].getVectorP()));
            }
        }

        for (int k = 0; k < globalData.getNumberOfElements(); k++) {
            //for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                vectorPGlobal[0][grid.getElements()[k].getID()[j].getNodeID() - 1] += grid.getElements()[k].getVectorP()[0][j];
            }
            //}
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

    public void printPGlobal() {
        System.out.println("VectorP Global:");
        System.out.println(Arrays.deepToString(vectorPGlobal));
    } //print for debug

}
