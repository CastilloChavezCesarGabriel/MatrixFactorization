package controller;

import model.Matrix;
import view.View;
import javax.swing.*;

public class Controller {
    private final View view;
    private JTextField[][] matrixFields;
    private JTextField[] vectorFields;

    public Controller(View view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getCreateMatrixButton().addActionListener(_ -> createMatrixFields());
        view.getSolveButton().addActionListener(_ -> solveEquation());
    }

    private void createMatrixFields() {
        int size = view.getMatrixSize();
        if (size <= 0) {
            JOptionPane.showMessageDialog(view, "Please enter a valid matrix size (positive integer)");
            return;
        }

        matrixFields = new JTextField[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixFields[i][j] = new JTextField(5);
                matrixFields[i][j].setText("0");
            }
        }

        vectorFields = new JTextField[size];
        for (int i = 0; i < size; i++) {
            vectorFields[i] = new JTextField(5);
            vectorFields[i].setText("0");
        }

        view.show(matrixFields,vectorFields);
    }

    private void solveEquation() {
        try {
            int size = view.getMatrixSize();
            if (size <= 0) {
                view.showOutput("Error: Invalid matrix size");
                return;
            }

            double[][] A = new double[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    A[i][j] = Double.parseDouble(matrixFields[i][j].getText());
                }
            }

            double[] b = new double[size];
            for (int i = 0; i < size; i++) {
                b[i] = Double.parseDouble(vectorFields[i].getText());
            }

            Matrix matrix = new Matrix(size);
            matrix.factorize(A);
            double[] y = matrix.forwardSubstitute(b);
            double[] x = matrix.backwardSubstitute(y);

            StringBuilder result = new StringBuilder("Solution vector:\n");
            for (int i = 0; i < x.length; i++) {
                result.append("x").append(i + 1).append(" = ").append(x[i]).append("\n");
            }

            result.append("\nMatrix L:\n");
            double[][] L = matrix.getMatrixL();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    result.append(String.format("%8.4f", L[i][j])).append(" ");
                }
                result.append("\n");
            }

            result.append("\nMatrix U:\n");
            double[][] U = matrix.getMatrixU();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    result.append(String.format("%8.4f", U[i][j])).append(" ");
                }
                result.append("\n");
            }

            view.showOutput(result.toString());
        } catch (NumberFormatException e) {
            view.showOutput("Error: Please enter valid numbers in all fields");
        } catch (Exception e) {
            view.showOutput("Error: " + e.getMessage());
        }
    }
}