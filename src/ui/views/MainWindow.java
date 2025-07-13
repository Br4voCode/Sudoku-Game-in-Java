package ui.views;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import ui.components.BoardPanel;
import logic.SudokuController;
import model.Board;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private SudokuController controller;

    public MainWindow() {
        super("Sudoku Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        controller = new SudokuController();
        controller.loadBoardFromMatrix(getSampleEasyBoard());

        BoardPanel boardPanel = controller.getBoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        // Puedes añadir botones o menú en el futuro
        pack();
        setLocationRelativeTo(null); // Centrar
    }

    private int[][] getSampleEasyBoard() {
        return new int[][] {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
    }
}

