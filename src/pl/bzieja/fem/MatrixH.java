package pl.bzieja.fem;

import pl.bzieja.fem.mathlogic.MatrixOperations;

public class MatrixH {
    double[][] dNByDX;
    double[][] dNByDY;
    double[][][] matrixH; //[integration points] -> matrixH[4][4]
    double k = 30;

    //for one element and its each integration point
    public MatrixH(Jacobian jacobian, UniversalElement universalElement) {
        dNByDX = new double[universalElement.getNumberOfAllIntegrationPoints()][4];
        dNByDY = new double[universalElement.getNumberOfAllIntegrationPoints()][4];
        matrixH = new double[universalElement.getNumberOfAllIntegrationPoints()][4][4];


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

        //calculate H matrices. We want to obtain 4x4 H Matrix
        for (int i = 0; i < 4; i++) {
            double[][][] componentMatricesH = new double[4][4][4];

            for (int j = 0; j < 4; j++) {
                double[][] currentDNByDXVector = MatrixOperations.getVectorFromMatrix(dNByDX, j);
                double[][] currentDNByDYVector = MatrixOperations.getVectorFromMatrix(dNByDY, j);

                double[][] dNByDX_dNByDXT = MatrixOperations.multiplyMatrices(currentDNByDXVector, MatrixOperations.transposeVector(currentDNByDXVector));
                double[][] dNByDY_dNByDYT = MatrixOperations.multiplyMatrices(currentDNByDYVector, MatrixOperations.transposeVector(currentDNByDYVector));

                componentMatricesH[j] = MatrixOperations.multiplyMatrixByConstant(MatrixOperations.sumMatrices(dNByDX_dNByDXT, dNByDY_dNByDYT), k * jacobian.getJacobianDeterminantsMatrix()[i] * universalElement.getWeightsOfIntegrationPoints()[i % universalElement.getNumberOfIntegrationPoints()]);
                matrixH[i] = MatrixOperations.sumMatrices(matrixH[i], componentMatricesH[j]);
            }
        }




    }


}
