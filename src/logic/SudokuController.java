package logic;

import model.Board;
import ui.components.BoardPanel;

public class SudokuController {

    private Board board;            // Tablero que ve el usuario
    private Board solutionBoard;    // Tablero solución
    private BoardPanel boardPanel;
    private BoardGenerator generator;

    public SudokuController() {
        this.generator = new BoardGenerator();
        this.board = new Board();
        this.boardPanel = new BoardPanel(board);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public void generateNewPuzzle(int holes) {
        generator.generateCompleteBoard();      // Genera un tablero completo
        generator.removeNumbers(holes);          // Remueve 'holes' números
        this.board = generator.getBoard();       // Actualiza referencia del tablero
        this.boardPanel.setBoard(board);         // Actualiza tablero en panel
        boardPanel.refreshFromModel();            // Refresca vista
    }

    public void loadBoardFromMatrix(int[][] matrix) {
        board.loadFromMatrix(matrix);
        boardPanel.refreshFromModel();
    }

    public void loadSolutionFromMatrix(int[][] matrix) {
        solutionBoard.loadFromMatrix(matrix);
    }

    public boolean isCorrectMove(int row, int col, int value) {
        return solutionBoard.getCell(row, col).getValue() == value;
    }

    public boolean isBoardComplete() {
        return board.isComplete();
    }

    public boolean isBoardValid() {
        return board.isValid();
    }

    public void resetBoard() {
        board.loadFromMatrix(new int[9][9]);
        boardPanel.refreshFromModel();
    }

    public Board getBoard() {
        return board;
    }

    public Board getSolutionBoard() {
        return solutionBoard;
    }
}
