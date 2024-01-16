package com.taylorchyi.sudoku.dto;

public class CellPosition {
    private int row;
    private int column;
    private static final int MIN = 0;
    private static final int MAX = Integer.MAX_VALUE;

    public CellPosition() {}

    public CellPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isValidCellPosition() {
        return isRowInBounds() && isColumnInBounds();
    }

    public boolean isRowInBounds() {
        return this.row >= MIN && this.row <= MAX;
    }

    public boolean isColumnInBounds() {
        return this.column >= MIN && this.column <= MAX;
    }
}
