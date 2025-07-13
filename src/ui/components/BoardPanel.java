package ui.components;

import javax.swing.*;

import model.Board;
import model.Cell;

import java.awt.*;

public class BoardPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private Board board;
    private CellComponent[][] components;

    public BoardPanel(Board board) {
        this.board = board;
        this.components = new CellComponent[9][9];
        setLayout(new GridLayout(9, 9));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        buildGrid();
    }

    private void buildGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Cell cell = board.getCell(row, col);
                CellComponent cellComp = new CellComponent(cell);

                // Optional: Add thicker borders between 3x3 boxes
                int top = (row % 3 == 0) ? 2 : 1;
                int left = (col % 3 == 0) ? 2 : 1;
                int bottom = (row == 8) ? 2 : 1;
                int right = (col == 8) ? 2 : 1;
                cellComp.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                components[row][col] = cellComp;
                add(cellComp);
            }
        }
    }

    public void refreshFromModel() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                components[row][col].updateFromCell();
            }
        }
    }

    public void clearBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Cell cell = board.getCell(row, col);
                if (cell.isEditable()) {
                    cell.setValue(0);
                    components[row][col].updateFromCell();
                }
            }
        }
    }

    public Board getBoard() {
        return board;
    }
}

