package pl.bzieja.fem;

public class MatrixH {
    double[][] derivativesOfShapesFunctionsByX;
    double[][] derivativesOfShapesFunctionsByY;

    public MatrixH(Element element, UniversalElement universalElement) {
        derivativesOfShapesFunctionsByX = new double[universalElement.getNumberOfIntegrationPoints()][4];
        derivativesOfShapesFunctionsByY = new double[universalElement.getNumberOfIntegrationPoints()][4];

        for (int i = 0; i < universalElement.getNumberOfAllIntegrationPoints(); i++) {
            for (int j = 0; j < 4; j++) {
                //derivativesOfShapesFunctionsByX[i][j] =

            }
        }


    }



}
