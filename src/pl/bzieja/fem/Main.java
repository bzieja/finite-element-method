package pl.bzieja.fem;

import pl.bzieja.fem.mathlogic.GaussIntegration;
import pl.bzieja.fem.mathlogic.MatrixOperations;

public class Main {

    public static void main(String[] args) {
        String pathToDataFile = "data.txt";
        GlobalData globalData = new GlobalData(pathToDataFile);
        Grid grid = new Grid(globalData);
        grid.fillGrid(globalData);
        grid.validateElement(12);


        int integrationPoints = 2;
        UniversalElement universalElement = new UniversalElement(integrationPoints);
        Jacobian jacobian = new Jacobian(new Element(new Node(0, 0), new Node(4, 0), new Node(4, 6), new Node(0,6)), universalElement);

        MatrixH matrixH = new MatrixH(jacobian, universalElement);
        //////lab 4


        /*double[][] m1 = new double[][] {{9, 6}, {3, 7}, {2, 1}};
        double[][] m2 = new double[][] {{1, 4, 6},{8, 2, 4}};


        double[][] result = MatrixOperations.multiplyMatrixes(m1, m2);*/



        //debugging jacobian
        //UniversalElement universalElement = new UniversalElement(2);
        //Jacobian jacobian = new Jacobian(new Element(new Node(0, 0), new Node(4, 0), new Node(4, 6), new Node(0,6)), universalElement, 0);

        //debugging Gauss Integration
        //GaussIntegration gaussIntegration = new GaussIntegration(-1, 1, 2);
        //gaussIntegration.calculate();
    }
}
