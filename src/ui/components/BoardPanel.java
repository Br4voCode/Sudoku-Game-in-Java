package ui.components;

import intefaces.CellValueValidator;

import javax.swing.*;
import javax.swing.border.Border;

import logic.SudokuController;
import model.Board;
import model.Cell;

import java.awt.*;

public class BoardPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int GRID_SIZE = 9;
    private static final Border BOARD_BORDER = BorderFactory.createLineBorder(Color.BLACK, 2);

    private Board board;
    private final CellComponent[][] components;
    private CellComponent selectedCell;
    private SudokuController controller;

    public BoardPanel(Board board) {
        this.board = board;
        this.components = new CellComponent[GRID_SIZE][GRID_SIZE];
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        setBorder(BOARD_BORDER);
        buildGrid();
    }
    
    @Override
    public Dimension getPreferredSize() {
        // Usa el menor entre ancho y alto del contenedor para mantener proporción cuadrada
        Container parent = getParent();
        if (parent != null) {
            int size = Math.min(parent.getWidth(), parent.getHeight());
            return new Dimension(size, size);
        }
        return new Dimension(450, 450); // valor por defecto
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(300, 300); // tamaño mínimo aceptable del tablero
    }
    
    public void setController(final SudokuController controller) {
        this.controller = controller;
        // Ahora que está asignado, puedes usarlo
        forEachCell(new CellAction() {
            public void apply(int row, int col, CellComponent cell) {
                cell.setValidator(new CellValueValidator() {
                    public boolean isCorrectValue(int r, int c, int v) {
                        return controller.isCorrectMove(r, c, v);
                    }
                });
            }
        });
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

    public void setBoard(Board newBoard) {
        this.board = newBoard;
        // Actualiza las celdas con el nuevo tablero
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                components[row][col].setCell(board.getCell(row, col));
            }
        }
        repaint();
    } 

    public Board getBoard() {
        return board;
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

    // Pequeña interfaz funcional interna (sin usar lambdas)
    private interface CellAction {
        void apply(int row, int col, CellComponent cell);
    }
}
