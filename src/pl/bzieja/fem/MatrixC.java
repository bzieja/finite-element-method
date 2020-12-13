package pl.bzieja.fem;

import pl.bzieja.fem.mathlogic.MatrixOperations;

public class MatrixC {
    UniversalElement universalElement;
    double[][] localMatrixC;
    double c;
    double ro;


    public MatrixC(Jacobian jacobian, UniversalElement universalElement, double c, double ro) {
        this.universalElement = universalElement;
        this.localMatrixC = new double[4][4];
        this.c = c;
        this.ro = ro;

        for (int i = 0; i < universalElement.getNumberOfAllIntegrationPoints(); i++) {
            double[][][] componentMatricesC = new double[universalElement.getNumberOfAllIntegrationPoints()][4][4]; //for debug only

            double[][] currentNVector = MatrixOperations.getRowAsHorizontalVectorFromMatrix(universalElement.getN(), i);
            double[][] currentNVectorT = MatrixOperations.transposeVector(currentNVector);

            componentMatricesC[i] = MatrixOperations.multiplyMatrixByConstant(MatrixOperations.multiplyMatrices(currentNVectorT, currentNVector), c * ro * jacobian.getJacobianDeterminantsMatrix()[i] * universalElement.getWeightsOfIntegrationPoints()[i % universalElement.getNumberOfIntegrationPoints()] * universalElement.getWeightsOfIntegrationPoints()[i / universalElement.getNumberOfIntegrationPoints()]);
            localMatrixC = MatrixOperations.sumMatrices(localMatrixC, componentMatricesC[i]);
        }
    }

    public double[][] getLocalMatrixC() {
        return localMatrixC;
    }
}
