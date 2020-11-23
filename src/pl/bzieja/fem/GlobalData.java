package pl.bzieja.fem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GlobalData {
    //read:
    private final double height;
    private final double width;
    private final int numberOfNodesAtHeight;
    private final int numberOfNodesAtWidth;
    private final double k;
    private final int numberOfIntegrationPoints;


    private final int numberOfNodes;
    private final int numberOfElements;

    public GlobalData(String pathToFile) {
        double[] inputData = new double[6];

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
}
