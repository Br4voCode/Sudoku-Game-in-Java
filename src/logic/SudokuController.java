package logic;

import model.Board;
import ui.components.BoardPanel;

public class SudokuController {

    private Board board;
    private BoardPanel boardPanel;

    public SudokuController() {
        this.board = new Board();
        this.boardPanel = new BoardPanel(board);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public void loadBoardFromMatrix(int[][] matrix) {
        board.loadFromMatrix(matrix);
        boardPanel.refreshFromModel();
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
}

