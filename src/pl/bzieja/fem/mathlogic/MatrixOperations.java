package pl.bzieja.fem.mathlogic;

import java.math.BigDecimal;

public class MatrixOperations {

    public static double[][] multiplyMatrices(double[][] m1, double[][] m2) {
        double[][] result = new double[m1.length][m2[0].length];

        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m1[0].length; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }

        return result;
    }

    public static double[][] multiplyMatrixByConstant(double[][] matrix, double constant) {
        double[][] result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                BigDecimal temp = new BigDecimal(matrix[i][j]);
                temp = temp.multiply(BigDecimal.valueOf(constant));
                result[i][j] = temp.doubleValue();
            }
        }

        return result;
    }

    public static double[][] getVectorFromMatrix(double[][] matrix, int column) {

        double[][] vector = new double[matrix.length][1];

        for (int i = 0; i < matrix.length; i++) {
            vector[i][0] = matrix[i][column];
        }

        return vector;
    }

    public static double[][] transposeVector(double[][] vector) {
        double[][] transposedVector = new double[1][vector.length];

        for (int i = 0; i < vector.length; i++) {
            transposedVector[0][i] = vector[i][0];
        }

        return  transposedVector;
    }

    public static double[][] sumMatrices(double[][] m1, double[][] m2) {

            double[][] result = new double[m1.length][m1[0].length];

            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m1.length; j++) {
                    result[i][j] = m1[i][j] + m2[i][j];
                }
            }
            return result;
    }

}