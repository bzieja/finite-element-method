package pl.bzieja.fem;

import pl.bzieja.fem.mathlogic.GaussIntegration;

public class Main {

    public static void main(String[] args) {
        String pathToDataFile = "data.txt";
        GlobalData globalData = new GlobalData(pathToDataFile);
        Grid grid = new Grid(globalData);
        grid.fillGrid(globalData);
        grid.validateElement(12);

        //debugging Gauss Integration
        //GaussIntegration gaussIntegration = new GaussIntegration(-1, 1, 2);
        //gaussIntegration.calculate();
    }
}
