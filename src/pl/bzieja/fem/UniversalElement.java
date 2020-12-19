package pl.bzieja.fem;

public class UniversalElement {

    private final int numberOfIntegrationPoints;

    private double[] integrationPointsXi;
    private double[] integrationPointsEta;
    private double[] weightsOfIntegrationPoints;

    private final double[][] dNByDXi;
    private final double[][] dNByDEta;

    private final double[][] N;


    public UniversalElement(int numberOfIntegrationPoints) {
        this.numberOfIntegrationPoints = numberOfIntegrationPoints;
        this.dNByDXi = new double[numberOfIntegrationPoints * numberOfIntegrationPoints][4];
        this.dNByDEta = new double[numberOfIntegrationPoints * numberOfIntegrationPoints][4];
        this.N = new double[numberOfIntegrationPoints * numberOfIntegrationPoints][4];

        System.out.println("Number of Integration Points: " + numberOfIntegrationPoints); //for debug ////////////////////////////////////////

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
            calculateNMatrix();

        } else if (numberOfIntegrationPoints == 3) {

            this.integrationPointsXi = new double[9];
            this.integrationPointsEta = new double[9];
            this.weightsOfIntegrationPoints = new double[] {5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0};

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

/*
            this.weightsOfIntegrationPoints = new double[] {5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0,
                    5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0,
                    5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0};
*/

            calculateDerivativesOfShapeFunctionAfterXiAndEta();
            calculateNMatrix();

        } else if (numberOfIntegrationPoints == 4){

            this.integrationPointsXi = new double[16];
            this.integrationPointsEta = new double[16];
            this.weightsOfIntegrationPoints = new double[] {(18.0 - Math.sqrt(30.0)) / 36.0, (18.0 + Math.sqrt(30.0)) / 36.0, (18.0 + Math.sqrt(30.0)) / 36.0, (18.0 - Math.sqrt(30.0)) / 36.0,};

            this.integrationPointsXi[0] = -Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[1] = -Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[2] = Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[3] = Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));

            this.integrationPointsXi[4] = -Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[5] = -Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[6] = Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[7] = Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));

            this.integrationPointsXi[8] = -Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[9] = -Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[10] = Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[11] = Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));

            this.integrationPointsXi[12] = -Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[13] = -Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[14] = Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsXi[15] = Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));

            ////////////////
            this.integrationPointsEta[0] = -Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[1] = -Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[2] = -Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[3] = -Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));

            this.integrationPointsEta[4] = -Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[5] = -Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[6] = -Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[7] = -Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));

            this.integrationPointsEta[8] = Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[9] = Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[10] = Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[11] = Math.sqrt(3.0 / 7.0 - (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));

            this.integrationPointsEta[12] = Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[13] = Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[14] = Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));
            this.integrationPointsEta[15] = Math.sqrt(3.0 / 7.0 + (2.0  / 7.0) * Math.sqrt(6.0 / 5.0));

            calculateDerivativesOfShapeFunctionAfterXiAndEta();
            calculateNMatrix();

        } else {
            System.out.println("Wrong number of integration points!");
        }
    }

    private void calculateDerivativesOfShapeFunctionAfterXiAndEta() {

        int numberOfRowsInMatrix = numberOfIntegrationPoints * numberOfIntegrationPoints; //each integration point is a row

        for (int i = 0; i < numberOfRowsInMatrix; i++) {
            dNByDXi[i][0] = -0.25 * (1 - integrationPointsEta[i]);
            dNByDXi[i][1] =  0.25 * (1 - integrationPointsEta[i]);
            dNByDXi[i][2] =  0.25 * (1 + integrationPointsEta[i]);
            dNByDXi[i][3] = -0.25 * (1 + integrationPointsEta[i]);

            dNByDEta[i][0] = -0.25 * (1 - integrationPointsXi[i]);
            dNByDEta[i][1] = -0.25 * (1 + integrationPointsXi[i]);
            dNByDEta[i][2] =  0.25 * (1 + integrationPointsXi[i]);
            dNByDEta[i][3] =  0.25 * (1 - integrationPointsXi[i]);
        }
    }

    private void calculateNMatrix() { //needed for matrix C

        int numberOfRowsInMatrix = numberOfIntegrationPoints * numberOfIntegrationPoints; //each integration point is a row

        for (int i = 0; i < numberOfRowsInMatrix; i++) {
            N[i][0] = 0.25 * (1 - integrationPointsEta[i]) * (1 - integrationPointsXi[i]);
            N[i][1] = 0.25 * (1 + integrationPointsEta[i]) * (1 - integrationPointsXi[i]);
            N[i][2] = 0.25 * (1 + integrationPointsEta[i]) * (1 + integrationPointsXi[i]);
            N[i][3] = 0.25 * (1 - integrationPointsEta[i]) * (1 + integrationPointsXi[i]);
        }

    }

    public double[][] getdNByDXi() {
        return dNByDXi;
    }

    public double[][] getdNByDEta() {
        return dNByDEta;
    }

    public int getNumberOfAllIntegrationPoints() {
        return numberOfIntegrationPoints * numberOfIntegrationPoints;
    }

    public int getNumberOfIntegrationPoints() {
        return numberOfIntegrationPoints;
    }

    public double[] getWeightsOfIntegrationPoints() {
        return weightsOfIntegrationPoints;
    }

    public double[][] getN() {
        return N;
    }

    public double[] getIntegrationPointsXi() {
        return integrationPointsXi;
    }

    public double[] getIntegrationPointsEta() {
        return integrationPointsEta;
    }
}
