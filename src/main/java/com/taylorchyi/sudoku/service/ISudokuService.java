package com.taylorchyi.sudoku.service;

import com.taylorchyi.sudoku.model.SudokuBoard;

public interface ISudokuService {

    SudokuBoard generateNewGame(int difficultyLevel);

    SudokuBoard getCurrentBoard();

    void clearAllSettedCellNumber();

    boolean isValidInput(int row, int col, int number);

    boolean isGameFinished();

    void setCellNumber(int row, int col, int number);

    void clearCellNumber(int row, int col);

    void setCellCandidate(int row, int col, int candidate);

    void clearCellAllCandidates(int row, int col);

    int getHint(int row, int col);

    boolean isOriginalCell(int row, int column);

    SudokuBoard generate(int difficultyLevel);
}

