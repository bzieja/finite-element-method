package pl.bzieja.fem.applogic;

import pl.bzieja.fem.gridlogic.Element;

public class Jacobian {

    double[] dXByDXi; //for each integration point
    double[] dXByDEta; //for each integration point
    double[] dYByDXi; //for each integration point
    double[] dYByDEta; //for each integration point
    double[][][] jacobianMatrix; //[integration point] -> jacobian[][]
    double[][][] invertedJacobianMatrix; //[integration point] -> invertedJacobian[][]
    double[] jacobianDeterminantsMatrix; //[integration point] -> value

    //only for one element and its each integration point
    public Jacobian (Element element, UniversalElement universalElement) {

        this.jacobianMatrix = new double[universalElement.getNumberOfAllIntegrationPoints()][2][2];
        this.dXByDXi = new double[universalElement.getNumberOfAllIntegrationPoints()];
        this.dXByDEta = new double[universalElement.getNumberOfAllIntegrationPoints()];
        this.dYByDXi = new double[universalElement.getNumberOfAllIntegrationPoints()];
        this.dYByDEta = new double[universalElement.getNumberOfAllIntegrationPoints()];

        for (int i = 0; i < universalElement.getNumberOfAllIntegrationPoints(); i++) {
            dXByDXi[i] = 0;
            dXByDEta[i] = 0;
            dYByDXi[i] = 0;
            dYByDEta[i] = 0;

            for (int j = 0; j < 4; j++) { //because we have 4 apexes (and because of that we have 4 shape functions)
                dXByDXi[i] += universalElement.getdNByDXi()[i][j] * element.getID()[j].getX();
                dXByDEta[i] += universalElement.getdNByDEta()[i][j] * element.getID()[j].getX();
                dYByDXi[i] += universalElement.getdNByDXi()[i][j] * element.getID()[j].getY();
                dYByDEta[i] += universalElement.getdNByDEta()[i][j] * element.getID()[j].getY();
            }

            jacobianMatrix[i][0][0] = dXByDXi[i];
            jacobianMatrix[i][0][1] = dYByDXi[i];
            jacobianMatrix[i][1][0] = dXByDEta[i];
            jacobianMatrix[i][1][1] = dYByDEta[i];

        }

        calculateDeterminants(universalElement.getNumberOfAllIntegrationPoints());
        invertJacobians();
    }

    void calculateDeterminants(int numberOfIntegrationPoints) { //only two-dimensional determinants
        jacobianDeterminantsMatrix = new double[numberOfIntegrationPoints];

        for (int i = 0; i < numberOfIntegrationPoints; i++) {
            jacobianDeterminantsMatrix[i] = jacobianMatrix[i][0][0] * jacobianMatrix[i][1][1] - jacobianMatrix[i][0][1] * jacobianMatrix[i][1][0];
        }
    }

    void invertJacobians() {
        invertedJacobianMatrix = new double[jacobianMatrix.length][2][2];
        for (int i = 0; i < jacobianMatrix.length; i++) {
            invertedJacobianMatrix[i][0][0] =  jacobianMatrix[i][1][1];
            invertedJacobianMatrix[i][0][1] = -jacobianMatrix[i][0][1];
            invertedJacobianMatrix[i][1][0] = -jacobianMatrix[i][1][0];
            invertedJacobianMatrix[i][1][1] =  jacobianMatrix[i][0][0];

        }
    }

    public double[][][] getJacobianMatrix() {
        return jacobianMatrix;
    }

    public double[][][] getInvertedJacobianMatrix() {
        return invertedJacobianMatrix;
    }

    public double[] getJacobianDeterminantsMatrix() {
        return jacobianDeterminantsMatrix;
    }
}
