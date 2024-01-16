package com.taylorchyi.sudoku.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.taylorchyi.sudoku.model.SudokuBoard;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    private SudokuBoard sudokuBoard;

    @BeforeEach
    void setUp() {
        sudokuBoard = new SudokuBoard();
    }

    @Test
    void testSudokuBoardInitialization() {
        for (int i = 0; i < SudokuBoard.size(); i++) {
            for (int j = 0; j < SudokuBoard.size(); j++) {
                assertNotNull(sudokuBoard.getCell(i, j), "Cell should not be null");
            }
        }
    }

    // 添加更多测试案例，例如测试行、列、子网格的有效性等
}
