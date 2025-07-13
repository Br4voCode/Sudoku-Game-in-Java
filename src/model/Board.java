package model;

public class Board {
    private Cell[][] cells;

    public Board() {
        cells = new Cell[9][9];
        initializeCells();
    }

    private void initializeCells() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row > 8 || col < 0 || col > 8) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return cells[row][col];
    }

    public void setCell(int row, int col, Cell cell) {
        if (row < 0 || row > 8 || col < 0 || col > 8) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        cells[row][col] = cell;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void loadFromMatrix(int[][] matrix) {
        if (matrix.length != 9 || matrix[0].length != 9) {
            throw new IllegalArgumentException("Matrix must be 9x9");
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = matrix[row][col];
                Cell cell = cells[row][col];
                cell.setValue(value);
                cell.setEditable(value == 0); // only empty cells are editable
            }
        }
    }

    public boolean isComplete() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValid() {
        return checkRows() && checkColumns() && checkBoxes();
    }

    private boolean checkRows() {
        for (int row = 0; row < 9; row++) {
            boolean[] seen = new boolean[10];
            for (int col = 0; col < 9; col++) {
                int value = cells[row][col].getValue();
                if (value != 0) {
                    if (seen[value]) return false;
                    seen[value] = true;
                }
            }
        }
        return true;
    }

    private boolean checkColumns() {
        for (int col = 0; col < 9; col++) {
            boolean[] seen = new boolean[10];
            for (int row = 0; row < 9; row++) {
                int value = cells[row][col].getValue();
                if (value != 0) {
                    if (seen[value]) return false;
                    seen[value] = true;
                }
            }
        }
        return true;
    }

    private boolean checkBoxes() {
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                boolean[] seen = new boolean[10];
                for (int row = boxRow * 3; row < boxRow * 3 + 3; row++) {
                    for (int col = boxCol * 3; col < boxCol * 3 + 3; col++) {
                        int value = cells[row][col].getValue();
                        if (value != 0) {
                            if (seen[value]) return false;
                            seen[value] = true;
                        }
                    }
                }
            }
        }
        return true;
    }
}

