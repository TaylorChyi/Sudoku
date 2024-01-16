package com.taylorchyi.sudoku.model;

import com.taylorchyi.sudoku.dto.CellPosition;

public class SudokuBoard {
    private SudokuCell[][] board;
    private static final int SIZE = 9;

    public SudokuBoard() {
        this.board = new SudokuCell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = new SudokuCell();
            }
        }
    }

    public static int size() {
        return SIZE;
    }

    public SudokuCell[][] getBoard() {
        return board;
    }

    public void setBoard(SudokuCell[][] board) {
        this.board = board;
    }

    public void setCell(int row, int col, int number) {
        board[row][col].setNumber(number);
    }

    public SudokuCell getCell(int row, int col) {
        return board[row][col];
    }

    private boolean isRowValid(int row) {
        boolean[] seen = new boolean[SIZE + 1];
        for (int i = 0; i < SIZE; i++) {
            int number = board[row][i].getNumber();
            if (number != 0) {
                if (seen[number]) {
                    return false;
                }
                seen[number] = true;
            }
        }
        return true;
    }

    private boolean isColValid(int col) {
        boolean[] seen = new boolean[SIZE + 1]; 
        for (int i = 0; i < SIZE; i++) {
            int number = board[i][col].getNumber();
            if (number != 0) {
                if (seen[number]) {
                    return false;
                }
                seen[number] = true;
            }
        }
        return true;
    }

    private boolean isBoxValid(int startRow, int startCol) {
        boolean[] seen = new boolean[SIZE + 1]; 
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int number = board[startRow + row][startCol + col].getNumber();
                if (number != 0) {
                    if (seen[number]) {
                        return false;
                    }
                    seen[number] = true;
                }
            }
        }
        return true;
    }

    public boolean isValid() {
        for (int i = 0; i < SIZE; i++) {
            if (!isRowValid(i) || !isColValid(i) || !isBoxValid(i - i % 3, i % 3 * 3)) {
                return false;
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public SudokuCell[][] getBoardArray() {
        return this.board;
    }
    
    public CellPosition randomEmptyCellPosition() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!board[row][col].hasNumber()) {
                    return new CellPosition(row, col);
                }
            }
        }

        return new CellPosition(-1, -1);
    }
}

