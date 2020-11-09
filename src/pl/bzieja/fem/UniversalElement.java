package pl.bzieja.fem;

public class UniversalElement {

    private int numberOfIntegrationPoints;

    private double[] integrationPointsXi;
    private double[] integrationPointsEta;
    private double[] weightsOfIntegrationPoints;

    private final double [][] derivativesOfShapesFunctionsByXi;
    private final double [][] derivativesOfShapesFunctionsByEta;


    public UniversalElement(int numberOfIntegrationPoints) {
        this.numberOfIntegrationPoints = numberOfIntegrationPoints;
        this.derivativesOfShapesFunctionsByXi = new double[numberOfIntegrationPoints * numberOfIntegrationPoints][4];
        this.derivativesOfShapesFunctionsByEta = new double[numberOfIntegrationPoints * numberOfIntegrationPoints][4];

        if (numberOfIntegrationPoints == 2) {
            this.integrationPointsXi = new double[4];
            this.integrationPointsEta = new double[4];
            this.weightsOfIntegrationPoints = new double[] {1.0, 1.0};

            this.integrationPointsXi[0] = -(1.0 / Math.sqrt(3.0));
            this.integrationPointsXi[1] = 1.0 / Math.sqrt(3.0);
            this.integrationPointsXi[2] = 1.0 / Math.sqrt(3.0);
            this.integrationPointsXi[3] = -(1.0 / Math.sqrt(3.0));

            this.integrationPointsEta[0] = -(1.0 / Math.sqrt(3.0));
            this.integrationPointsEta[1] = -(1.0 / Math.sqrt(3.0));
            this.integrationPointsEta[2] = 1.0 / Math.sqrt(3.0);
            this.integrationPointsEta[3] = 1.0 / Math.sqrt(3.0);

            calculateDerivativesOfShapeFunctionAfterXiAndEta();
        } else if (numberOfIntegrationPoints == 3) {

            this.integrationPointsXi = new double[9];
            this.integrationPointsEta = new double[9];

            this.integrationPointsXi[0] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsXi[1] = 0.0;
            this.integrationPointsXi[2] = Math.sqrt(3.0 / 5.0);
            this.integrationPointsXi[3] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsXi[4] = 0.0;
            this.integrationPointsXi[5] = Math.sqrt(3.0 / 5.0);
            this.integrationPointsXi[6] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsXi[7] = 0.0;
            this.integrationPointsXi[8] = Math.sqrt(3.0 / 5.0);

            this.integrationPointsEta[0] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsEta[1] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsEta[2] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsEta[3] = 0.0;
            this.integrationPointsEta[4] = 0.0;
            this.integrationPointsEta[5] = 0.0;
            this.integrationPointsEta[6] = Math.sqrt(3.0 / 5.0);
            this.integrationPointsEta[7] = Math.sqrt(3.0 / 5.0);
            this.integrationPointsEta[8] = Math.sqrt(3.0 / 5.0);

            this.weightsOfIntegrationPoints = new double[] {5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0};

            calculateDerivativesOfShapeFunctionAfterXiAndEta();
        } else {
            System.out.println("Wrong number of integration points!");
        }
    }

    private void calculateDerivativesOfShapeFunctionAfterXiAndEta() {

        int numberOfRowsInMatrix = numberOfIntegrationPoints * numberOfIntegrationPoints; //each integration point is a row

        for (int i = 0; i < numberOfRowsInMatrix; i++) {
            derivativesOfShapesFunctionsByXi[i][0] = -0.25 * (1 - integrationPointsEta[i]);
            derivativesOfShapesFunctionsByXi[i][1] =  0.25 * (1 - integrationPointsEta[i]);
            derivativesOfShapesFunctionsByXi[i][2] =  0.25 * (1 + integrationPointsEta[i]);
            derivativesOfShapesFunctionsByXi[i][3] = -0.25 * (1 + integrationPointsEta[i]);

            derivativesOfShapesFunctionsByEta[i][0] = -0.25 * (1 - integrationPointsXi[i]);
            derivativesOfShapesFunctionsByEta[i][1] = -0.25 * (1 + integrationPointsXi[i]);
            derivativesOfShapesFunctionsByEta[i][2] =  0.25 * (1 + integrationPointsXi[i]);
            derivativesOfShapesFunctionsByEta[i][3] =  0.25 * (1 - integrationPointsXi[i]);
        }
    }

    public double[][] getDerivativesOfShapesFunctionsByXi() {
        return derivativesOfShapesFunctionsByXi;
    }

    public double[][] getDerivativesOfShapesFunctionsByEta() {
        return derivativesOfShapesFunctionsByEta;
    }

    public int getNumberOfAllIntegrationPoints() {
        return numberOfIntegrationPoints * numberOfIntegrationPoints;
    }

    public int getNumberOfIntegrationPoints() {
        return numberOfIntegrationPoints;
    }

}
