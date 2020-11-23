package pl.bzieja.fem;

import pl.bzieja.fem.mathlogic.MatrixOperations;

public class MatrixH {
    private double[][] dNByDX;
    private double[][] dNByDY;
    private double[][] localMatrixH; //[integration points] -> matrixH[4][4]
    private double k;

    //for one element and its each integration point
    public MatrixH(Jacobian jacobian, UniversalElement universalElement, double k) {
        dNByDX = new double[universalElement.getNumberOfAllIntegrationPoints()][4];
        dNByDY = new double[universalElement.getNumberOfAllIntegrationPoints()][4];
        localMatrixH = new double[4][4];
        this.k = k;

        for (int i = 0; i < universalElement.getNumberOfAllIntegrationPoints(); i++) {

            dNByDX[i][0] = (1 / jacobian.getJacobianDeterminantsMatrix()[i]) * (universalElement.getdNByDXi()[i][0] * jacobian.getInvertedJacobianMatrix()[i][0][0]
                + universalElement.getdNByDEta()[i][0] * jacobian.getInvertedJacobianMatrix()[i][0][1]);
            dNByDX[i][1] = (1 / jacobian.getJacobianDeterminantsMatrix()[i]) * (universalElement.getdNByDXi()[i][1] * jacobian.getInvertedJacobianMatrix()[i][0][0]
                +  universalElement.getdNByDEta()[i][1] * jacobian.getInvertedJacobianMatrix()[i][0][1]);
            dNByDX[i][2] = (1 / jacobian.getJacobianDeterminantsMatrix()[i]) * (universalElement.getdNByDXi()[i][2] * jacobian.getInvertedJacobianMatrix()[i][0][0]
                +  universalElement.getdNByDEta()[i][2] * jacobian.getInvertedJacobianMatrix()[i][0][1]);
            dNByDX[i][3] = (1 / jacobian.getJacobianDeterminantsMatrix()[i]) * (universalElement.getdNByDXi()[i][3] * jacobian.getInvertedJacobianMatrix()[i][0][0]
                +  universalElement.getdNByDEta()[i][3] * jacobian.getInvertedJacobianMatrix()[i][0][1]);


            dNByDY[i][0] = (1 / jacobian.getJacobianDeterminantsMatrix()[i]) * (universalElement.getdNByDXi()[i][0] * jacobian.getInvertedJacobianMatrix()[i][1][0]
                + universalElement.getdNByDEta()[i][0] * jacobian.getInvertedJacobianMatrix()[i][1][1]);
            dNByDY[i][1] = (1 / jacobian.getJacobianDeterminantsMatrix()[i]) * (universalElement.getdNByDXi()[i][1] * jacobian.getInvertedJacobianMatrix()[i][1][0]
                + universalElement.getdNByDEta()[i][1] * jacobian.getInvertedJacobianMatrix()[i][1][1]);
            dNByDY[i][2] = (1 / jacobian.getJacobianDeterminantsMatrix()[i]) * (universalElement.getdNByDXi()[i][2] * jacobian.getInvertedJacobianMatrix()[i][1][0]
                + universalElement.getdNByDEta()[i][2] * jacobian.getInvertedJacobianMatrix()[i][1][1]);
            dNByDY[i][3] = (1 / jacobian.getJacobianDeterminantsMatrix()[i]) * (universalElement.getdNByDXi()[i][3] * jacobian.getInvertedJacobianMatrix()[i][1][0]
                + universalElement.getdNByDEta()[i][3] * jacobian.getInvertedJacobianMatrix()[i][1][1]);

        }

        //calculate 4x4 H[i] Matrix for each integration point
        for (int i = 0; i < universalElement.getNumberOfAllIntegrationPoints(); i++) {
            double[][][] componentMatricesH = new double[universalElement.getNumberOfAllIntegrationPoints()][4][4]; //for debugging only

            double[][] currentDNByDXVector = MatrixOperations.getRowAsHorizontalVectorFromMatrix(dNByDX, i);
            double[][] currentDNByDYVector = MatrixOperations.getRowAsHorizontalVectorFromMatrix(dNByDY, i);

            double[][] dNByDX_dNByDXT = MatrixOperations.multiplyMatrices(MatrixOperations.transposeVector(currentDNByDXVector), currentDNByDXVector);
            double[][] dNByDY_dNByDYT = MatrixOperations.multiplyMatrices(MatrixOperations.transposeVector(currentDNByDYVector), currentDNByDYVector);

            //componentMatricesH[i] = MatrixOperations.multiplyMatrixByConstant(MatrixOperations.sumMatrices(dNByDX_dNByDXT, dNByDY_dNByDYT), k * jacobian.getJacobianDeterminantsMatrix()[i] * universalElement.getWeightsOfIntegrationPoints()[i % universalElement.getNumberOfIntegrationPoints()]);
            componentMatricesH[i] = MatrixOperations.multiplyMatrixByConstant(MatrixOperations.sumMatrices(dNByDX_dNByDXT, dNByDY_dNByDYT), this.k * jacobian.getJacobianDeterminantsMatrix()[i] * universalElement.getWeightsOfIntegrationPoints()[i % universalElement.getNumberOfIntegrationPoints()] * universalElement.getWeightsOfIntegrationPoints()[i / universalElement.getNumberOfIntegrationPoints()]);
            localMatrixH = MatrixOperations.sumMatrices(localMatrixH, componentMatricesH[i]);
        }
    }

    public double[][] getLocalMatrixH() {
        return localMatrixH;
    }

}
