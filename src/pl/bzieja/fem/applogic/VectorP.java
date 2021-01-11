package pl.bzieja.fem.applogic;

import pl.bzieja.fem.gridlogic.Element;
import pl.bzieja.fem.gridlogic.Node;
import pl.bzieja.fem.mathlogic.MatrixOperations;

public class VectorP {
    private double[][] vectorP;

    private int numberOfIntegrationPoints;
    private double[] integrationPointsXi;
    private double[] integrationPointsEta;
    private double[] weightsOfIntegrationPoints;
    private double[][] N;
    double alfa;
    double tAlfa;
    private double currentSideLength;

    public VectorP(Element element, UniversalElement universalElement, double alfa, double tAlfa) {

        this.alfa = alfa;
        this.tAlfa = tAlfa;
        this.vectorP = new double[1][4];
        this.numberOfIntegrationPoints = universalElement.getNumberOfIntegrationPoints();


        //if down -> eta = -1
        if (element.getID()[0].isBoundaryCondition() && element.getID()[1].isBoundaryCondition()) {

            calculateDistanceBetweenPoints(element.getID()[0], element.getID()[1]);
            calculateIntegrationPoints();
            for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
                this.integrationPointsEta[i] = -1.0;
            }

            calculateNMatrix();
            calculateVectorP();
        }

        //if right -> xi = 1
        if (element.getID()[1].isBoundaryCondition() && element.getID()[2].isBoundaryCondition()) {

            calculateDistanceBetweenPoints(element.getID()[1], element.getID()[2]);
            calculateIntegrationPoints();
            for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
                this.integrationPointsXi[i] = 1.0;
            }

            calculateNMatrix();
            calculateVectorP();
        }

        //if up -> eta = 1
        if (element.getID()[2].isBoundaryCondition() && element.getID()[3].isBoundaryCondition()) {

            calculateDistanceBetweenPoints(element.getID()[2], element.getID()[3]);
            calculateIntegrationPoints();
            for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
                this.integrationPointsEta[i] = 1.0;
            }

            calculateNMatrix();
            calculateVectorP();
        }

        //if left -> xi = -1
        if (element.getID()[0].isBoundaryCondition() && element.getID()[3].isBoundaryCondition()) {

            calculateDistanceBetweenPoints(element.getID()[0], element.getID()[3]);
            calculateIntegrationPoints();
            for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
                this.integrationPointsXi[i] = -1.0;
            }

            calculateNMatrix();
            calculateVectorP();
        }
    }

    private void calculateIntegrationPoints() {

        if (this.numberOfIntegrationPoints == 2) {

            this.integrationPointsXi = new double[2];
            this.integrationPointsEta = new double[2];
            this.weightsOfIntegrationPoints = new double[]{1.0, 1.0};

            this.integrationPointsXi[0] = -(1.0 / Math.sqrt(3.0));
            this.integrationPointsXi[1] = 1.0 / Math.sqrt(3.0);

            this.integrationPointsEta[0] = -(1.0 / Math.sqrt(3.0));
            this.integrationPointsEta[1] = 1.0 / Math.sqrt(3.0);

        } else if (numberOfIntegrationPoints == 3) {

            this.integrationPointsXi = new double[3];
            this.integrationPointsEta = new double[3];
            this.weightsOfIntegrationPoints = new double[]{5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0};

            this.integrationPointsXi[0] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsXi[1] = 0.0;
            this.integrationPointsXi[2] = Math.sqrt(3.0 / 5.0);

            this.integrationPointsEta[0] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsEta[1] = 0.0;
            this.integrationPointsEta[2] = Math.sqrt(3.0 / 5.0);

        } else if (numberOfIntegrationPoints == 4) {

            this.integrationPointsXi = new double[4];
            this.integrationPointsEta = new double[4];
            this.weightsOfIntegrationPoints = new double[]{(18.0 - Math.sqrt(30.0)) / 36.0, (18.0 + Math.sqrt(30.0)) / 36.0, (18.0 + Math.sqrt(30.0)) / 36.0, (18.0 - Math.sqrt(30.0)) / 36.0,};

            this.integrationPointsXi[0] = -Math.sqrt(3.0 / 7.0 + (2.0 / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[1] = -Math.sqrt(3.0 / 7.0 - (2.0 / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[2] = Math.sqrt(3.0 / 7.0 - (2.0 / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[3] = Math.sqrt(3.0 / 7.0 + (2.0 / 7.0) * Math.sqrt(6.0 / 5.0));

            this.integrationPointsEta[0] = -Math.sqrt(3.0 / 7.0 + (2.0 / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[1] = -Math.sqrt(3.0 / 7.0 - (2.0 / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[2] = Math.sqrt(3.0 / 7.0 - (2.0 / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[3] = Math.sqrt(3.0 / 7.0 + (2.0 / 7.0) * Math.sqrt(6.0 / 5.0));
        }


    }

    private void calculateNMatrix() {

        this.N = new double[numberOfIntegrationPoints][4];

        for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
            this.N[i][0] = 0.25 * (1 - integrationPointsXi[i]) * (1 - integrationPointsEta[i]);
            this.N[i][1] = 0.25 * (1 + integrationPointsXi[i]) * (1 - integrationPointsEta[i]);
            this.N[i][2] = 0.25 * (1 + integrationPointsXi[i]) * (1 + integrationPointsEta[i]);
            this.N[i][3] = 0.25 * (1 - integrationPointsXi[i]) * (1 + integrationPointsEta[i]);
        }
    }

    private void calculateVectorP() {

        for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
            double[][] componentOfVectorP = new double[1][4];

            double[][] NVectorForCurrentIntegrationPoint = MatrixOperations.getRowAsHorizontalVectorFromMatrix(this.N, i);

            componentOfVectorP = MatrixOperations.multiplyMatrixByConstant(NVectorForCurrentIntegrationPoint, -1 * this.alfa * this.weightsOfIntegrationPoints[i] * (this.currentSideLength / 2.0) * this.tAlfa);

            this.vectorP = MatrixOperations.sumMatrices(this.vectorP, componentOfVectorP);
        }

    }

    public double[][] getVectorP() {
        return vectorP;
    }

    private void calculateDistanceBetweenPoints(Node n1, Node n2) {
        double x1 = n1.getX();
        double y1 = n1.getY();
        double x2 = n2.getX();
        double y2 = n2.getY();

        this.currentSideLength = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

}
