package pl.bzieja.fem.mathlogic;

public class GaussIntegration {

    double integrationIntervalStart;
    double integrationIntervalEnd;
    int numberOfIntegrationPoints; //it will choose integration scheme

    double integrationIntervalLength;

    double[] integrationPointsX;
    double[] integrationPointsY;
    double[] integrationPointsWeights;
    double[][] functionValuesAtTheIntegrationPoints;

    public GaussIntegration(double integrationIntervalStart, double integrationIntervalEnd, int numberOfIntegrationPoints) {
        this.integrationIntervalStart = integrationIntervalStart;
        this.integrationIntervalEnd = integrationIntervalEnd;
        this.numberOfIntegrationPoints = numberOfIntegrationPoints;

        this.integrationIntervalLength = integrationIntervalEnd - integrationIntervalStart;
        this.integrationPointsWeights = new double[numberOfIntegrationPoints];
        this.integrationPointsX = new double[numberOfIntegrationPoints];
        this.integrationPointsY = new double[numberOfIntegrationPoints];
        this.functionValuesAtTheIntegrationPoints = new double[numberOfIntegrationPoints][numberOfIntegrationPoints];

        if (numberOfIntegrationPoints == 2) {
            this.integrationPointsX[0] = -(1.0 / Math.sqrt(3.0));
            this.integrationPointsX[1] = 1.0 / Math.sqrt(3.0);

            this.integrationPointsY[0] = -(1.0 / Math.sqrt(3.0));
            this.integrationPointsY[1] = 1.0 / Math.sqrt(3.0);

            integrationPointsWeights[0] = 1.0;
            integrationPointsWeights[1] = 1.0;

        } else if (numberOfIntegrationPoints == 3) {

            this.integrationPointsX[0] = -(Math.sqrt(3.0 / 5.0));
            this.integrationPointsX[1] = 0.0;
            this.integrationPointsX[2] = Math.sqrt(3.0 / 5.0);

            this.integrationPointsY[0] = -(Math.sqrt(3.0 / 5.0));;
            this.integrationPointsY[1] = 0.0;
            this.integrationPointsY[2] = Math.sqrt(3.0 / 5.0);

            integrationPointsWeights[0] = 5.0 / 9.0;
            integrationPointsWeights[1] = 8.0 / 9.0;
            integrationPointsWeights[2] = 5.0 / 9.0;
        }
    }

    public double calculate() {

        double integralValue = 0;

        //calculate values of function in integration points
        for (int i = 0; i < numberOfIntegrationPoints; i++) {
            for (int j = 0; j < numberOfIntegrationPoints; j++) {
                this.functionValuesAtTheIntegrationPoints[i][j] = calculateValueOfFunction(integrationPointsX[i], integrationPointsY[j]);
            }
        }

        for (int i = 0; i < numberOfIntegrationPoints; i++) {
            for (int j = 0; j < numberOfIntegrationPoints; j++) {
                integralValue += functionValuesAtTheIntegrationPoints[i][j] * integrationPointsWeights[i] * integrationPointsWeights[j];
            }
        }

        return integralValue;
    }

    double calculateValueOfFunction(double x, double y) {
        //function formula
        return 2.0 * x + 3.0 * y + 4.0;
    }

}
