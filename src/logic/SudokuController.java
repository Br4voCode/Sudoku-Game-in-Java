package logic;

import model.Board;
import ui.components.BoardPanel;

public class SudokuController {

    private Board board;            // Tablero que ve el usuario
    private Board solutionBoard;    // Tablero solución
    private BoardPanel boardPanel;
    private BoardGenerator generator;

    public SudokuController() {
        this.board = new Board();
        this.generator = new BoardGenerator(); 
        this.boardPanel = new BoardPanel(board);
        this.boardPanel.setController(this); 
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public void generateNewPuzzle(int holes) {
        Board full = generator.generateCompleteBoard();
        this.solutionBoard = full.clone();                 // Guardar copia de solución
        generator.removeNumbers(holes);                    // Modifica solo la copia interna
        this.board = generator.getBoard();                 // Obtiene el puzzle incompleto
        this.boardPanel.setBoard(board);
        boardPanel.refreshFromModel();
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
