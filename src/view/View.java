package view;
import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private final JTextField sizeInputField;
    private final JButton createMatrixButton;
    private final JPanel matrixPanel;
    private final JButton solveButton;
    private final JTextArea outputArea;

    public View() {
        setTitle("LU Factorization Solver - Doolittle");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel sizeInputPanel = new JPanel(new FlowLayout());
        sizeInputPanel.add(new JLabel("Size of the matrix:"));
        sizeInputField = new JTextField(5);
        sizeInputPanel.add(sizeInputField);

        createMatrixButton = new JButton("Create Matrix");
        sizeInputPanel.add(createMatrixButton);

        matrixPanel = new JPanel();
        matrixPanel.setLayout(new BoxLayout(matrixPanel, BoxLayout.Y_AXIS));
        matrixPanel.add(new JLabel("Matrix: A"));
        matrixPanel.setVisible(false);

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);

        solveButton = new JButton("Solve");
        solveButton.setVisible(false);

        add(sizeInputPanel, BorderLayout.NORTH);
        add(matrixPanel, BorderLayout.CENTER);
        add(solveButton, BorderLayout.SOUTH);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(solveButton, BorderLayout.NORTH);
        southPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    public int getMatrixSize() {
        try {
            return Integer.parseInt(sizeInputField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public JButton getCreateMatrixButton() {
        return createMatrixButton;
    }

    public JButton getSolveButton() {
        return solveButton;
    }

    public void show(JTextField[][] matrixFields, JTextField[] vectorFields) {
        matrixPanel.removeAll();

        JPanel title = new JPanel(new GridLayout(1, 2));
        title.add(new JLabel("Matrix A"));
        title.add(new JLabel("Vector B"));
        matrixPanel.add(title);

        JPanel input = new JPanel(new GridLayout(matrixFields.length, matrixFields.length + 1));
        for (int i = 0; i < matrixFields.length; i++) {
            for (int j = 0; j < matrixFields[i].length; j++) {
                input.add(matrixFields[i][j]);
            }
            input.add(vectorFields[i]);
        }

        matrixPanel.add(input);
        matrixPanel.setVisible(true);
        solveButton.setVisible(true);
        revalidate();
        repaint();
    }

    public void showOutput(String result) {
        outputArea.setText(result);
    }
}