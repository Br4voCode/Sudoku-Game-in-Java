package model;

public class Cell {
    private int row;
    private int col;
    private String id;
    private int value; // 0 means empty
    private boolean editable;

    public Cell(int row, int col) {
        setRow(row);
        setCol(col);
        this.id = row + ":" + col;
        this.value = 0;
        this.editable = true;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        if (row < 0 || row > 8) {
            throw new IllegalArgumentException("Row must be between 0 and 8");
        }
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        if (col < 0 || col > 8) {
            throw new IllegalArgumentException("Column must be between 0 and 8");
        }
        this.col = col;
    }

    public String getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Cell value must be between 0 and 9");
        }
        this.value = value;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}

