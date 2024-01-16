# Sudoku

## Overview

This Sudoku project is a comprehensive solution for both solving and playing Sudoku puzzles. It is implemented in Java, leveraging best practices in object-oriented programming and design patterns. The project is structured to offer both a command-line interface for interaction and a robust backend logic for puzzle processing.

## Key Components

**ISudokuService**: Interface defining the core functionalities for the Sudoku service.

**SudokuServiceImpl**: Implementation of the Sudoku service, handling the logic for solving puzzles.

**Environment**: Configurations and environment settings for the Sudoku game.

**SudokuBoard**: Represents the Sudoku board, managing the grid and cell values.

**SudokuCell**: Represents an individual cell in the Sudoku board.

**CellPosition**: Utility class for handling cell positions on the Sudoku board.

**SudokuController**: Controller class that interfaces between the user input and the backend logic.

**SudokuCLI**: Command Line Interface for interacting with the Sudoku game and solver.

## Features

- Solve Sudoku puzzles using advanced algorithms.
- Play Sudoku with an intuitive command-line interface.
- Customizable difficulty levels for gameplay.
- Error checking and validation of user inputs.
- Interactive and user-friendly CLI for a seamless experience.

## Technology Stack

- Language: Java
- Design Patterns: MVC (Model-View-Controller), Singleton, Factory
- Testing: JUnit for unit testing
- Version Control: Git
- Getting Started

## Prerequisites

- Java JDK 8 or higher
- Maven (for dependency management and build)

## Installation

### Clone the repository

```bash
git clone https://github.com/TaylorChyi/Sudoku.git
```

### Navigate to the project directory and build the project using Maven

```bash
cd [project directory]
mvn clean install
```

## Running the Application

### To start the Sudoku game

```bash
java -jar target/sudoku-game.jar
```

## Usage

- Follow the on-screen instructions in the CLI to play the game or solve puzzles.
- Enter puzzle values or select options as prompted.

## Contribution

Contributions to this project are welcome. Please follow the standard Git workflow - fork the repository, make changes, and submit a pull request.

## License

GPL-3.0 license

## Contact

<taylor_chyi@icloud.com>
