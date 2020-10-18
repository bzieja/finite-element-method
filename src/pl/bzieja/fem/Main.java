package pl.bzieja.fem;

public class Main {

    public static void main(String[] args) {
        String pathToDataFile = "data.txt";
        GlobalData globalData = new GlobalData(pathToDataFile);
        Grid grid = new Grid(globalData);
        grid.fillGrid(globalData);
        grid.validateElement(12);

    }
}
