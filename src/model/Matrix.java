package model;

public class Matrix {
    private final double[][] matrixL;
    private final double[][] matrixU;
    private final int size;

    public Matrix(int size) {
        this.size = size;
        this.matrixL = new double[size][size];
        this.matrixU = new double[size][size];
    }

    public double[][] getMatrixL() {
        return matrixL;
    }

    public double[][] getMatrixU() {
        return matrixU;
    }

    public void factorize(double[][] A) {
        for (int i = 0; i < size; i++) {
            for (int k = i; k < size; k++) {
                double add = 0;
                for (int j = 0; j < i; j++) {
                    add += matrixL[i][j] * matrixU[j][k];
                }
                matrixU[i][k] = A[i][k] - add;
            }

            for (int k = i; k < size; k++) {
                if (i == k) {
                    matrixL[i][i] = 1;
                } else {
                    double add = 0;
                    for (int j = 0; j < i; j++) {
                        add += matrixL[k][j] * matrixU[j][i];
                    }
                    matrixL[k][i] = (A[k][i] - add) / matrixU[i][i];
                }
            }
        }
    }

    public double[] forwardSubstitute(double[] b) {
        double[] y = new double[size];
        for (int i = 0; i < size; i++) {
            double add = 0;
            for (int j = 0; j < i; j++) {
                add += matrixL[i][j] * y[j];
            }
            y[i] = b[i] - add;
        }
        return y;
    }

    public double[] backwardSubstitute(double[] y) {
        double[] x = new double[size];
        for (int i = size - 1; i >= 0; i--) {
            double add = 0;
            for (int j = i + 1; j < size; j++) {
                add += matrixU[i][j] * x[j];
            }
            x[i] = (y[i] - add) / matrixU[i][i];
        }
        return x;
    }
}
