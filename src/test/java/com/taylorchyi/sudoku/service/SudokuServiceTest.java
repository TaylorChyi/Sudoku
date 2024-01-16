package com.taylorchyi.sudoku.service;

import com.taylorchyi.sudoku.model.SudokuBoard;
import com.taylorchyi.sudoku.service.ISudokuGenerator;
import com.taylorchyi.sudoku.service.SudokuService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class SudokuServiceTest {

    @Mock
    private ISudokuGenerator sudokuGenerator;

    private SudokuService sudokuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sudokuService = new SudokuService(sudokuGenerator);
    }

    @Test
    void generateNewGame_ShouldReturnValidBoard() {
        SudokuBoard mockBoard = new SudokuBoard();
        when(sudokuGenerator.generate(1)).thenReturn(mockBoard);
        
        SudokuBoard result = sudokuService.generateNewGame(1);
        assertNotNull(result, "Generated SudokuBoard should not be null");
    }

    @Test
    void checkSolution_WhenCorrect_ShouldReturnTrue() {
        // 假设生成一个已知是正确的数独解决方案
        SudokuBoard correctSolution = new SudokuBoard(); // 填充具有有效解决方案的细节
        assertTrue(sudokuService.checkSolution(correctSolution),
                   "Check solution should return true for a correct solution");
    }

    @Test
    void checkSolution_WhenIncorrect_ShouldReturnFalse() {
        // 假设生成一个已知是错误的数独解决方案
        SudokuBoard incorrectSolution = new SudokuBoard(); // 填充具有无效解决方案的细节
        assertFalse(sudokuService.checkSolution(incorrectSolution),
                    "Check solution should return false for an incorrect solution");
    }
}
