package pl.bzieja.fem;

import pl.bzieja.fem.applogic.UniversalElement;
import pl.bzieja.fem.gridlogic.Grid;

public class Main {

    public static void main(String[] args) {
        String pathToDataFile = "data.txt";
        GlobalData globalData = new GlobalData(pathToDataFile);
        Grid grid = new Grid(globalData);


        SOE soe = new SOE(grid, globalData);
        soe.startSimulation();
        soe.printMatrixHGlobal();
        soe.printMatrixCGlobal();
        soe.printPGlobal();
    }

    /////////// debug for given point ////////////
        /*
        int integrationPoints = 2;
        UniversalElement universalElement = new UniversalElement(integrationPoints);
        Jacobian jacobian = new Jacobian(new Element(new Node(0, 0), new Node(4, 0), new Node(4, 6), new Node(0,6)), universalElement);
        MatrixH matrixH = new MatrixH(jacobian, universalElement);
         */

}
