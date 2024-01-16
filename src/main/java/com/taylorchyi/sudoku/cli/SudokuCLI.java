package com.taylorchyi.sudoku.cli;

import java.util.Scanner;

import com.taylorchyi.sudoku.controller.SudokuController;
import com.taylorchyi.sudoku.dto.CellPosition;
import com.taylorchyi.sudoku.model.SudokuBoard;
import com.taylorchyi.sudoku.model.SudokuCell;
import com.taylorchyi.sudoku.serviceimpl.SudokuServiceImpl;

public class SudokuCLI {

	private static SudokuController controller;
    private static Scanner scanner;

    enum GameState {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }
    
    private static GameState gameState = GameState.NOT_STARTED;
    private static boolean isRunning = true;

    public static void main(String[] args) {
        controller = new SudokuController(new SudokuServiceImpl());
        scanner = new Scanner(System.in);

        while (isRunning) {
            printMenu();
            int choice = getIntInput(0, 6);
    
            switch (gameState) {
                case NOT_STARTED:
                    handleNotStartedState(choice);
                    break;
                case IN_PROGRESS:
                    handleInProgressState(choice);
                    break;
                case COMPLETED:
                    handleCompletedState(choice);
                    break;
            }
        }
        scanner.close();
    }

    private static void quitGame() {
        isRunning = false;
    }

    private static void handleNotStartedState(int choice) {
        switch (choice) {
            case 1:
                startNewGame();
                break;
            case 0:
                quitGame();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void handleInProgressState(int choice) {
        switch (choice) {
            case 1:
                resetGame();
                break;
            case 2:
                setCellNumber();
                checkGameStatus();
                break;
            case 3:
                clearCellNumber();
                checkGameStatus();
                break;
            case 4:
                printBoard();
                break;
            case 5:
                autoCompleteGame();
                checkGameStatus();
                break;
            case 6:
                provideHint();
                checkGameStatus();
                break;
            case 0:
                quitGame();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void handleCompletedState(int choice) {
        switch (choice) {
            case 1:
                startNewGame();
                break;
            case 2:
                resetGame();
                break;
            case 0:
                quitGame();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void printMenu() {
        switch (gameState) {
            case NOT_STARTED:
                printNotStartedMenu();
                break;
            case IN_PROGRESS:
                printInProgressMenu();
                break;
            case COMPLETED:
                printCompletedMenu();
                break;
        }
        System.out.print("Enter your choice: ");
    }

    private static void printNotStartedMenu() {
        System.out.println("\n--- Sudoku Game Menu (Game Not Started)---");
        System.out.println("1. Start New Game");
        System.out.println("0. Exit");
    }

    private static void printInProgressMenu() {
        System.out.println("\\n--- Sudoku Game Menu (Game In Progress)---");
        System.out.println("1. Reset Current Game");
        System.out.println("2. Set Cell Number");
        System.out.println("3. Clear Cell Number");
        System.out.println("4. Print Current Board");
        System.out.println("5. Auto Complete Game");
        System.out.println("6. Get a Hint");
        System.out.println("0. Exit");
    }

    private static void printCompletedMenu() {
        System.out.println("\\n--- Sudoku Game Menu (Game Completed)---");
        System.out.println("1. Start New Game");
        System.out.println("2. Reset Current Game");
        System.out.println("0. Exit");
    }

    private static int getIntInput(int min, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ": ");
                }
            } else {
                System.out.println("Invalid input. Please enter a number: ");
                scanner.next(); // 消耗非整数输入
            }
        }
    }

    private static void checkGameStatus() {
        if (controller.isGameFinished()) {
            System.out.println("Congratulations! You have completed the game.");
            gameState = GameState.COMPLETED;
        } else {
            System.out.println("Game is still in progress.");
        }
    }

    private static void provideHint() {
        int row = inputRowNumber();
        int column = inputColumnNumber();
        if (controller.isOriginalCell(row, column)) {
            int hintCellNumber = controller.getHint(new CellPosition(row, column));
            System.out.println("Hint: Try placing " + hintCellNumber + " in row " + (row + 1) + ", column " + (column + 1));
            setCellNumber(row, column, hintCellNumber);
        }
        else {
            System.out.println("Original number need not hint.");
        }
    }

    private static void autoCompleteGame() {
        CellPosition cellPosition = controller.getCurrentBoard().randomEmptyCellPosition();

        if (cellPosition.isValidCellPosition()) {
            int row = cellPosition.getRow();
            int column = cellPosition.getColumn();
            int hintCellNumber = controller.getHint(new CellPosition(row, column));
            System.out.println("Hint: Try placing " + hintCellNumber + " in row " + (row + 1) + ", column " + (column + 1));
            setCellNumber(row, column, hintCellNumber);
        }
        else {
            System.out.println("There is no more hint.");
        }
    }
       
	private static void startNewGame() {
		int difficultyLevel = inputDifficultyLevel();
		SudokuBoard newGameBoard = controller.generateGame(difficultyLevel);
		System.out.println("New game started at difficulty level " + difficultyLevel);
        gameState = GameState.IN_PROGRESS;
		printBoardState(newGameBoard);
	}

    private static int inputDifficultyLevel() {
        System.out.print("Enter difficulty level (1-Easy, 2-Medium, 3-Hard): ");
        return getIntInput(1, 3);
    }

	private static void printBoard() {
		SudokuBoard currentBoard = controller.getCurrentBoard();
		printBoardState(currentBoard);
	}
	
    private static void printBoardState(SudokuBoard board) {
        System.out.println("    1 2 3   4 5 6   7 8 9"); // 列号
        System.out.println("  +-------+-------+-------+");
        for (int i = 0; i < 9; i++) {
            System.out.print((i + 1) + " | "); // 行号
            for (int j = 0; j < 9; j++) {
                SudokuCell cell = board.getCell(i, j);
                if (cell.hasNumber()) {
                    System.out.print(cell.getNumber() + " ");
                } else {
                    System.out.print(". ");
                }
                if ((j + 1) % 3 == 0) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((i + 1) % 3 == 0) {
                System.out.println("  +-------+-------+-------+");
            }
        }
    }
    
	private static void resetGame() {
		controller.resetGame();
		System.out.println("Game has been reset.");
		printBoard();
	}
	
	private static void setCellNumber() {
        int row = inputRowNumber();
        int column = inputColumnNumber();
        int cellNumber = inputCellNumber();

        if (controller.isOriginalCell(row, column)) {
            controller.setCellNumber(new CellPosition(row, column), cellNumber);
            System.out.println("Number set in cell.");
        } else {
            System.out.println("Original number cannot be modified.");
        }
		printBoard();
	}

    private static void setCellNumber(int row, int column, int cellNumber) {
        if (controller.isOriginalCell(row, column)) {
            controller.setCellNumber(new CellPosition(row, column), cellNumber);
            System.out.println("Number set in cell.");
        } else {
            System.out.println("Original number cannot be modified.");
        }
		printBoard();
	}

    private static void clearCellNumber() {
        int row = inputRowNumber();
        int column = inputColumnNumber();
    
        if (controller.isOriginalCell(row, column)) {
            controller.clearCellNumber(new CellPosition(row, column));
            System.out.println("Cell number cleared.");
        } else {
            System.out.println("Original number cannot be cleared.");
        }
        printBoard();
    }

    private static int inputRowNumber() {
        System.out.print("Enter row (1-9): ");
        return getIntInput(1, 9) - 1;
    }

    private static int inputColumnNumber() {
		System.out.print("Enter column (1-9): ");
        return getIntInput(1, 9) - 1;
    }

    private static int inputCellNumber() {
		System.out.print("Enter number (1-9): ");
		return getIntInput(1, 9);
    }
    
}
