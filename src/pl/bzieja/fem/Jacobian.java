package pl.bzieja.fem;

public class Jacobian {

    double derivativeXByXi;
    double derivativeXByEta;
    double derivativeYByXi;
    double derivativeYByEta;
    double[][][] jacobianMatrix; //[integration point] -> jacobian[][]
    double[][][] invertedJacobianMatrix; //[integration point] -> invertedJacobian[][]
    double[] jacobianDeterminantsMatrix; //[integration point] -> value

    //only for one element and its each integration point
    public Jacobian (Element element, UniversalElement universalElement, int pc) {

        this.jacobianMatrix = new double[universalElement.getNumberOfAllIntegrationPoints()][2][2];
        for (int i = 0; i < universalElement.getNumberOfAllIntegrationPoints(); i++) {
            derivativeXByXi = 0;
            derivativeXByEta = 0;
            derivativeYByXi = 0;
            derivativeYByEta = 0;

            for (int j = 0; j < 4; j++) { //because we have 4 apexes (and because of that we have 4 shape functions)
                derivativeXByXi += universalElement.getDerivativesOfShapesFunctionsByXi()[pc][j] * element.getID()[j].getX();
                derivativeXByEta += universalElement.getDerivativesOfShapesFunctionsByEta()[pc][j] * element.getID()[j].getX();
                derivativeYByXi += universalElement.getDerivativesOfShapesFunctionsByXi()[pc][j] * element.getID()[j].getY();
                derivativeYByEta += universalElement.getDerivativesOfShapesFunctionsByEta()[pc][j] * element.getID()[j].getY();
            }

            jacobianMatrix[i][0][0] = derivativeXByXi;
            jacobianMatrix[i][0][1] = derivativeYByXi;
            jacobianMatrix[i][1][0] = derivativeXByEta;
            jacobianMatrix[i][1][1] = derivativeYByEta;

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

}
