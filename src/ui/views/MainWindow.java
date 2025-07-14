package ui.views;

import javax.swing.JFrame;

import logic.SudokuController;
import ui.components.BoardPanel;

public class MainWindow extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SudokuController controller;

    public MainWindow() {
        super("Sudoku Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        controller = new SudokuController();
        BoardPanel boardPanel = controller.getBoardPanel();

        add(boardPanel);
        pack();
        setLocationRelativeTo(null);

        controller.generateNewPuzzle(55);
    }
}

