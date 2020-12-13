package pl.bzieja.fem;

import pl.bzieja.fem.gridlogic.Element;
import pl.bzieja.fem.gridlogic.Node;
import pl.bzieja.fem.mathlogic.MatrixOperations;

public class MatrixBoundaryConditions {

    private double[][] BCMatrix;

    private int numberOfIntegrationPoints;
    private double[] integrationPointsXi;
    private double[] integrationPointsEta;
    private double[] weightsOfIntegrationPoints;
    private double[][] N;
    double alfa;
    private double currentSideLength;


    public MatrixBoundaryConditions(Element element, UniversalElement universalElement, double alfa) {

        this.alfa = alfa;
        this.BCMatrix = new double[4][4];
        this.numberOfIntegrationPoints = universalElement.getNumberOfIntegrationPoints();


        //if down -> eta = -1
        if (element.getID()[0].isBoundaryCondition() && element.getID()[1].isBoundaryCondition()) {

            calculateDistanceBetweenPoints(element.getID()[0], element.getID()[1]);
            calculateIntegrationPoints();
            for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
                this.integrationPointsEta[i] = -1.0;
            }

            calculateNMatrix();
            calculateBCMatrix();
        }

        //if right -> xi = 1
        if (element.getID()[1].isBoundaryCondition() && element.getID()[2].isBoundaryCondition()) {

            calculateDistanceBetweenPoints(element.getID()[1], element.getID()[2]);
            calculateIntegrationPoints();
            for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
                this.integrationPointsXi[i] = 1.0;
            }

            calculateNMatrix();
            calculateBCMatrix();
        }

        //if up -> eta = 1
        if (element.getID()[2].isBoundaryCondition() && element.getID()[3].isBoundaryCondition()) {

            calculateDistanceBetweenPoints(element.getID()[2], element.getID()[3]);
            calculateIntegrationPoints();
            for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
                this.integrationPointsEta[i] = 1.0;
            }

            calculateNMatrix();
            calculateBCMatrix();
        }

        //if left -> xi = -1
        if (element.getID()[0].isBoundaryCondition() && element.getID()[3].isBoundaryCondition()) {

            calculateDistanceBetweenPoints(element.getID()[0], element.getID()[3]);
            calculateIntegrationPoints();
            for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
                this.integrationPointsXi[i] = -1.0;
            }

            calculateNMatrix();
            calculateBCMatrix();
        }

    }
    private void calculateIntegrationPoints( ) {

        if (this.numberOfIntegrationPoints == 2) {

            this.integrationPointsXi = new double[2];
            this.integrationPointsEta = new double[2];
            this.weightsOfIntegrationPoints = new double[] {1.0, 1.0};

            this.integrationPointsXi[0] = -(1.0 / Math.sqrt(3.0));
            this.integrationPointsXi[1] = 1.0 / Math.sqrt(3.0);

            this.integrationPointsEta[0] = -(1.0 / Math.sqrt(3.0));
            this.integrationPointsEta[1] = 1.0 / Math.sqrt(3.0);

        } else if (numberOfIntegrationPoints == 3) {

            this.integrationPointsXi = new double[3];
            this.integrationPointsEta = new double[3];
            this.weightsOfIntegrationPoints = new double[] {5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0};

            this.integrationPointsXi[0] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsXi[1] = 0.0;
            this.integrationPointsXi[2] = Math.sqrt(3.0 / 5.0);

            this.integrationPointsEta[0] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsEta[1] = 0.0;
            this.integrationPointsEta[2] = Math.sqrt(3.0 / 5.0);

        } else if (numberOfIntegrationPoints == 4){

            this.integrationPointsXi = new double[4];
            this.integrationPointsEta = new double[4];
            this.weightsOfIntegrationPoints = new double[] {(18.0 - Math.sqrt(30.0)) / 36.0, (18.0 + Math.sqrt(30.0)) / 36.0, (18.0 + Math.sqrt(30.0)) / 36.0, (18.0 - Math.sqrt(30.0)) / 36.0,};

            this.integrationPointsXi[0] = -Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[1] = -Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[2] = Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[3] = Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));

            this.integrationPointsEta[0] = -Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[1] = -Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[2] = Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[3] = Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
        }


    }

    private void calculateNMatrix() {

        this.N = new double[numberOfIntegrationPoints][4];

        for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
            this.N[i][0] = 0.25 * (1 - integrationPointsEta[i]) * (1 - integrationPointsXi[i]);
            this.N[i][1] = 0.25 * (1 + integrationPointsEta[i]) * (1 - integrationPointsXi[i]);
            this.N[i][2] = 0.25 * (1 + integrationPointsEta[i]) * (1 + integrationPointsXi[i]);
            this.N[i][3] = 0.25 * (1 - integrationPointsEta[i]) * (1 + integrationPointsXi[i]);
        }



    }

    private void calculateBCMatrix() {

        for (int i = 0; i < this.numberOfIntegrationPoints; i++) {
            double[][] componentOfBCMatrix = new double[4][4];

            double[][] NVectorForCurrentIntegrationPoint = MatrixOperations.getRowAsHorizontalVectorFromMatrix(this.N, i);
            double[][] NT = MatrixOperations.transposeVector(NVectorForCurrentIntegrationPoint);
            double[][] N_NT = MatrixOperations.multiplyMatrices(NT, NVectorForCurrentIntegrationPoint);

            componentOfBCMatrix = MatrixOperations.multiplyMatrixByConstant(N_NT, this.alfa * this.weightsOfIntegrationPoints[i] * this.currentSideLength / 2.0);

            this.BCMatrix = MatrixOperations.sumMatrices(this.BCMatrix, componentOfBCMatrix);
        }

    }

    public double[][] getBCMatrix() {
        return BCMatrix;
    }

    private void calculateDistanceBetweenPoints(Node n1, Node n2) {
        double x1 = n1.getX();
        double y1 = n1.getY();
        double x2 = n2.getX();
        double y2 = n2.getY();

        this.currentSideLength = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
