package pl.bzieja.fem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GlobalData {
    //read:
    private final double height;    //1
    private final double width;     //2
    private final int numberOfNodesAtHeight;    //3
    private final int numberOfNodesAtWidth;     //4
    private final double k;         //5
    private final int numberOfIntegrationPoints;    //6
    private final double c;         //7
    private final double ro;        //8
    private final double alfa;      //9


    private final int numberOfNodes;
    private final int numberOfElements;

    public GlobalData(String pathToFile) {
        int numberOfVariablesToRead = 9;
        double[] inputData = new double[numberOfVariablesToRead];

        try (Scanner reader = new Scanner(new File(pathToFile))) {
            for (int i = 0; reader.hasNextLine(); i++) {
                inputData[i] = Double.parseDouble(reader.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.height = inputData[0];
        this.width = inputData[1];
        this.numberOfNodesAtHeight = (int) inputData[2];
        this.numberOfNodesAtWidth = (int) inputData[3];
        this.k = inputData[4];
        this.numberOfIntegrationPoints = (int) inputData[5];
        this.c = inputData[6];
        this.ro = inputData[7];
        this.alfa = inputData[8];

        this.numberOfNodes = numberOfNodesAtHeight * numberOfNodesAtWidth;
        this.numberOfElements = (numberOfNodesAtHeight - 1) * (numberOfNodesAtWidth - 1);
    }

    public int getNumberOfNodesAtHeight() {
        return numberOfNodesAtHeight;
    }

    public int getNumberOfNodesAtWidth() {
        return numberOfNodesAtWidth;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public double getK() {
        return k;
    }

    public int getNumberOfIntegrationPoints() {
        return numberOfIntegrationPoints;
    }

    public double getC() {
        return c;
    }

    public double getRo() {
        return ro;
    }

    public double getAlfa() {
        return alfa;
    }
}
