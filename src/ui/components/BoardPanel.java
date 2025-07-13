package ui.components;

import javax.swing.*;
import javax.swing.border.Border;

import model.Board;
import model.Cell;

import java.awt.*;

public class BoardPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int GRID_SIZE = 9;
    private static final Border BOARD_BORDER = BorderFactory.createLineBorder(Color.BLACK, 2);

    private final Board board;
    private final CellComponent[][] components;
    private CellComponent selectedCell;

    public BoardPanel(Board board) {
        this.board = board;
        this.components = new CellComponent[GRID_SIZE][GRID_SIZE];
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        setBorder(BOARD_BORDER);
        buildGrid();
    }

    private void buildGrid() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Cell cell = board.getCell(row, col);
                CellComponent cellComp = new CellComponent(cell);
                components[row][col] = cellComp;
                applyBoxBorder(cellComp, row, col);
                add(cellComp);
            }
        }
    }

    private void applyBoxBorder(JComponent comp, int row, int col) {
        int top = (row % 3 == 0) ? 2 : 1;
        int left = (col % 3 == 0) ? 2 : 1;
        int bottom = (row == GRID_SIZE - 1) ? 2 : 1;
        int right = (col == GRID_SIZE - 1) ? 2 : 1;
        comp.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
    }

    public void refreshFromModel() {
        forEachCell(new CellAction() {
            public void apply(int row, int col, CellComponent cell) {
                cell.updateFromCell();
            }
        });
    }

    public void clearBoard() {
        forEachCell(new CellAction() {
            public void apply(int row, int col, CellComponent cell) {
                Cell modelCell = board.getCell(row, col);
                if (modelCell.isEditable()) {
                    modelCell.setValue(0);
                    cell.updateFromCell();
                }
            }
        });
    }

    public void handleCellSelection(CellComponent selected) {
        this.selectedCell = selected;
        Point coord = findCoordinatesOf(selected);
        if (coord == null) return;

        final int selectedValue = selected.getCell().getValue();
        final int selRow = coord.y;
        final int selCol = coord.x;
        final CellComponent selectedFinal = selected; // <--- Esto es necesario

        forEachCell(new CellAction() {
            public void apply(int row, int col, CellComponent cell) {
                boolean isSameCell = (cell == selectedFinal);
                boolean sameRowOrCol = (row == selRow || col == selCol);
                boolean sameValue = selectedValue != 0 &&
                        cell != selectedFinal &&
                        cell.getCell().getValue() == selectedValue;

                cell.setSelected(isSameCell);
                cell.setHighlightRowCol(sameRowOrCol);
                cell.setSameValue(sameValue);
            }
        });
    }

    private Point findCoordinatesOf(CellComponent target) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (components[row][col] == target) {
                    return new Point(col, row); // x = col, y = row
                }
            }
        }
        return null;
    }

    private void forEachCell(CellAction action) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                action.apply(row, col, components[row][col]);
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    // Pequeña interfaz funcional interna (sin usar lambdas)
    private interface CellAction {
        void apply(int row, int col, CellComponent cell);
    }
}
