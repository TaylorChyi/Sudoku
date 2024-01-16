package com.taylorchyi.sudoku.serviceimpl;

import com.taylorchyi.sudoku.model.SudokuBoard;
import com.taylorchyi.sudoku.model.SudokuCell;
import com.taylorchyi.sudoku.service.ISudokuService;

import java.util.HashMap;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class SudokuServiceImpl implements ISudokuService {

    private SudokuBoard currentBoard;
    private SudokuBoard initialBoard;
    private HashMap<String, Integer> solutionBoard;
    private SudokuBoard fullBoard;

    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private Random random = new Random();

    public SudokuServiceImpl() {
        this.currentBoard = new SudokuBoard();
        this.initialBoard = new SudokuBoard();
        this.fullBoard = new SudokuBoard();
    }

    @Override
    public SudokuBoard getCurrentBoard() {
        return currentBoard;
    }

    @Override
    public SudokuBoard generateNewGame(int difficultyLevel) {
        currentBoard = generate(difficultyLevel);
        initialBoard = copyBoard(currentBoard); 

        return currentBoard;
    }

    @Override
    public void clearAllSettedCellNumber() {
        currentBoard = new SudokuBoard();  // 重置为新的空白数独板
        currentBoard = copyBoard(initialBoard);
    }

    @Override
    public boolean isValidInput(int row, int col, int number) {
        SudokuBoard currentBoard = getCurrentBoard();
        // 检查同一行
        for (int c = 0; c < 9; c++) {
            if (currentBoard.getCell(row, c).getNumber() == number) {
                return false;
            }
        }
        // 检查同一列
        for (int r = 0; r < 9; r++) {
            if (currentBoard.getCell(r, col).getNumber() == number) {
                return false;
            }
        }
        // 检查同一宫格
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (currentBoard.getCell(r, c).getNumber() == number) {
                    return false;
                }
            }
        }
        return true;
    }
    

    @Override
    public boolean isGameFinished() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (currentBoard.getCell(i, j).getNumber() != fullBoard.getCell(i, j).getNumber()) {
                    return false;
                }
            }
        }
        return true;
    }
    

    @Override
    public void setCellNumber(int row, int col, int number) {
        SudokuCell cell = currentBoard.getCell(row, col);
        cell.setNumber(number);
        // 其他相关逻辑（如检查冲突）
    }

    @Override
    public void clearCellNumber(int row, int col) {
        SudokuCell cell = currentBoard.getCell(row, col);
        cell.setNumber(0);  // 清除数字
    }

    @Override
    public void setCellCandidate(int row, int col, int candidate) {
        SudokuCell cell = currentBoard.getCell(row, col);
        cell.addCandidate(candidate);
    }

    @Override
    public void clearCellAllCandidates(int row, int col) {
        currentBoard.getCell(row, col).clearCandidates();  // 清除所有备选数字
    }

    @Override
    public int getHint(int row, int col) {
        return solutionBoard.get(row + "_" + col);
    }

    @Override
    public boolean isOriginalCell(int row, int column) {
        SudokuCell initialCell = initialBoard.getCell(row, column);
        return !initialCell.hasNumber();
    }

    @Override
    public SudokuBoard generate(int difficultyLevel) {
        SudokuBoard board = new SudokuBoard();
        fillDiagonalSubgrids(board);
        fillRemainingCells(board, 0, SUBGRID_SIZE);
        this.fullBoard = copyBoard(board);
        this.solutionBoard = removeNumbersBasedOnDifficulty(board, difficultyLevel);
        return board;
    }

    // ------- 辅助方法 ------- 
    private SudokuBoard copyBoard(SudokuBoard board) {
        SudokuBoard newBoard = new SudokuBoard();
        for (int row = 0; row < SudokuBoard.size(); row++) {
            for (int col = 0; col < SudokuBoard.size(); col++) {
                SudokuCell originalCell = board.getCell(row, col);
                SudokuCell newCell = newBoard.getCell(row, col);
                newCell.setNumber(originalCell.getNumber());
                // 如果需要，复制其他状态，如备选数字
            }
        }
        return newBoard;
    }

    private void fillDiagonalSubgrids(SudokuBoard board) {
        for (int i = 0; i < SIZE; i += SUBGRID_SIZE) {
            fillSubgrid(board, i, i);
        }
    }

    private boolean fillSubgrid(SudokuBoard board, int row, int col) {
        int[] numbers = getRandomPermutation();
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                board.getCell(row + i, col + j).setNumber(numbers[SUBGRID_SIZE * i + j]);
            }
        }
        return true;
    }

    private boolean fillRemainingCells(SudokuBoard board, int i, int j) {
        if (j >= SIZE && i < SIZE - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= SIZE && j >= SIZE)
            return true;

        if (i < SUBGRID_SIZE) {
            if (j < SUBGRID_SIZE)
                j = SUBGRID_SIZE;
        } else if (i < SIZE - SUBGRID_SIZE) {
            if (j == (i / SUBGRID_SIZE) * SUBGRID_SIZE)
                j = j + SUBGRID_SIZE;
        } else {
            if (j == SIZE - SUBGRID_SIZE) {
                i = i + 1;
                j = 0;
                if (i >= SIZE)
                    return true;
            }
        }

        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(board, i, j, num)) {
                board.getCell(i, j).setNumber(num);
                if (fillRemainingCells(board, i, j + 1))
                    return true;
                board.getCell(i, j).setNumber(0);
            }
        }
        return false;
    }

    private boolean isSafe(SudokuBoard board, int row, int col, int num) {
        // 检查同一行是否安全
        for (int d = 0; d < SudokuBoard.size(); d++) {
            // 如果找到相同的数字，则返回 false
            if (board.getCell(row, d).getNumber() == num) {
                return false;
            }
        }

        // 检查同一列是否安全
        for (int r = 0; r < SudokuBoard.size(); r++) {
            // 如果找到相同的数字，则返回 false
            if (board.getCell(r, col).getNumber() == num) {
                return false;
            }
        }

        // 检查所在的 3x3 小区域是否安全
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int d = startCol; d < startCol + 3; d++) {
                if (board.getCell(r, d).getNumber() == num) {
                    return false;
                }
            }
        }

        // 如果以上检查都通过，则安全
        return true;
    }


    private int[] getRandomPermutation() {
        int[] permutation = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            permutation[i] = i + 1;
        }
        for (int i = SIZE - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = permutation[index];
            permutation[index] = permutation[i];
            permutation[i] = temp;
        }
        return permutation;
    }

    private HashMap<String, Integer> removeNumbersBasedOnDifficulty(SudokuBoard board, int difficultyLevel) {
        int cellsToRemove = getCellsToRemoveCount(difficultyLevel);
        HashMap<String, Integer> solutionBoard = new HashMap<>();
        while (cellsToRemove > 0) {
            int i = random.nextInt(SIZE);
            int j = random.nextInt(SIZE);
            if (board.getCell(i, j).hasNumber()) {
                solutionBoard.put(i + "_" + j, board.getCell(i, j).getNumber());
                board.getCell(i, j).setNumber(0);
                cellsToRemove--;
            }
        }
        return solutionBoard;
    }

    private int getCellsToRemoveCount(int difficultyLevel) {
        switch (difficultyLevel) {
            case 1: return 20; // Easy
            case 2: return 30; // Medium
            case 3: return 40; // Hard
            default: return 25; // Default
        }
    }
}
