package pl.bzieja.fem;

import pl.bzieja.fem.mathlogic.GaussIntegration;
import pl.bzieja.fem.mathlogic.MatrixOperations;

public class Main {

    public static void main(String[] args) {
        String pathToDataFile = "data.txt";
        GlobalData globalData = new GlobalData(pathToDataFile);
        Grid grid = new Grid(globalData);

        grid.fillGrid(globalData);
        UniversalElement universalElement = new UniversalElement(globalData.getNumberOfIntegrationPoints());

        SOE soe = new SOE(grid, globalData, universalElement);
        soe.printMatrixHGlobal();


        /////////// debug for given point ////////////
        /*
        int integrationPoints = 2;
        UniversalElement universalElement = new UniversalElement(integrationPoints);
        Jacobian jacobian = new Jacobian(new Element(new Node(0, 0), new Node(4, 0), new Node(4, 6), new Node(0,6)), universalElement);
        MatrixH matrixH = new MatrixH(jacobian, universalElement);
         */


    }
}
