package com.taylorchyi.sudoku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.taylorchyi.sudoku.dto.CellPosition;
import com.taylorchyi.sudoku.model.SudokuBoard;
import com.taylorchyi.sudoku.service.ISudokuService;

@RestController
@RequestMapping("/api/sudoku")
public class SudokuController {

    private final ISudokuService sudokuService;

    @Autowired
    public SudokuController(ISudokuService sudokuServiceImpl) {
        this.sudokuService = sudokuServiceImpl;
    }

    @GetMapping("/generateGame")
    public SudokuBoard generateGame(@RequestParam int difficultyLevel) {
        return sudokuService.generateNewGame(difficultyLevel);
    }

    @PostMapping("/resetGame")
    public SudokuBoard resetGame() {
        sudokuService.clearAllSettedCellNumber();
        return sudokuService.getCurrentBoard();
    }

    @GetMapping("/isValidInput")
    public boolean isValidInput(@RequestBody CellPosition position, @RequestParam int number) {
        return sudokuService.isValidInput(position.getRow(), position.getColumn(), number);
    }

    @GetMapping("/isGameFinished")
    public boolean isGameFinished() {
        return sudokuService.isGameFinished();
    }

    @PostMapping("/setCellNumber")
    public void setCellNumber(@RequestBody CellPosition position, @RequestParam int number) {
        sudokuService.setCellNumber(position.getRow(), position.getColumn(), number);
    }

    @PostMapping("/clearCellNumber")
    public void clearCellNumber(@RequestBody CellPosition position) {
        sudokuService.clearCellNumber(position.getRow(), position.getColumn());
    }

    @PostMapping("/setCellCandidate")
    public void setCellCandidate(@RequestBody CellPosition position, @RequestParam int candidate) {
        sudokuService.setCellCandidate(position.getRow(), position.getColumn(), candidate);
    }

    @PostMapping("/clearCellAllCandidates")
    public void clearCellAllCandidates(@RequestBody CellPosition position) {
        sudokuService.clearCellAllCandidates(position.getRow(), position.getColumn());
    }

    @GetMapping("/currentBoard")
    public SudokuBoard getCurrentBoard() {
        return sudokuService.getCurrentBoard();
    }

    @GetMapping("/getHint")
    public int getHint(@RequestBody CellPosition position) {
        return sudokuService.getHint(position.getRow(), position.getColumn());
    }

    @GetMapping("/isOriginalCell")
    public boolean isOriginalCell(int row, int column) {
        return sudokuService.isOriginalCell(row, column);
    }
}
