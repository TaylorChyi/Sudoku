package com.taylorchyi.sudoku.model;

import org.junit.jupiter.api.Test;

import com.taylorchyi.sudoku.model.SudokuCell;

import static org.junit.jupiter.api.Assertions.*;

class SudokuCellTest {

    @Test
    void testSudokuCellBehavior() {
        SudokuCell cell = new SudokuCell();
        cell.setNumber(5);
        assertEquals(5, cell.getNumber(), "Number should be set correctly");

        cell.addCandidate(3);
        assertTrue(cell.getCandidates().contains(3), "Candidate should be added correctly");

        cell.clear();
        assertEquals(0, cell.getNumber(), "Cell should be cleared correctly");
        assertTrue(cell.getCandidates().isEmpty(), "Candidates should be cleared correctly");
    }

    // 添加更多测试案例，例如测试备选数字的添加和移除等
}
