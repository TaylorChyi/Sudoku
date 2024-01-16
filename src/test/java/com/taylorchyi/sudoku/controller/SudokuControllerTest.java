package com.taylorchyi.sudoku.controller;

import com.taylorchyi.sudoku.model.SudokuBoard;
import com.taylorchyi.sudoku.service.SudokuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class SudokuControllerTest {

    @Mock
    private SudokuService sudokuService;

    private SudokuController sudokuController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sudokuController = new SudokuController(sudokuService);
    }

    @Test
    void testGenerateSudoku() {
        SudokuBoard mockBoard = new SudokuBoard();
        when(sudokuService.generateNewGame(1)).thenReturn(mockBoard);
        ResponseEntity<SudokuBoard> response = sudokuController.generateSudoku(1);
        assertEquals(mockBoard, response.getBody(), "Response body should contain generated SudokuBoard");
    }

    // 添加更多测试案例，例如检查解决方案、设置数字等
}
