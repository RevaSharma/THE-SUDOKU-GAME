/*
 * ****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: 02 - 11am
 * Date: 11/10/22
 * Time: 1:32 PM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.model
 * Class: SudokuGameLogic
 *
 * Description: Game engine that connects to the GUI for sudoku
 *
 * ****************************************
 */
package org.csci205Team08.model;

import java.util.Arrays;
import java.util.Set;
import java.util.Stack;


/**
 * Game engine for the Sudoku game
 */
public class SudokuGameLogic {
    /** {@link SudokuChecker} that checks if the Sudoku board satisfies sudoku's rule. */
    private SudokuChecker checker;

    /** Original sudoku board */
    private int[][] originalBoard;

    /** Current sudoku board */
    private int[][] workingBoard;

    /** Remaining cells to be filled */
    private int remainingCells;

    /** Number of mistakes */
    private int mistakesCounter;

    /** History of the movements */
    private Stack<SudokuMovement> history;

    /**
     * Constructor of the sudoku game
     */
    public SudokuGameLogic(){
        this.checker = new SudokuChecker();
        this.history = new Stack<>();
    }

    /**
     * Initialize new Sudoku game
     * @param quizBoard the original Sudoku board
     */
    public void initGame(int[][] quizBoard) {
        this.originalBoard = copySudokuBoard(quizBoard);
        this.workingBoard = copySudokuBoard(quizBoard);
        this.remainingCells = 0;
        this.mistakesCounter = 0;
        this.history = new Stack<>();

        countRemainingCells();
    }

    /**
     * Attempts to put a number onto a current board
     * Updates the board if and only if the number is correct
     * @param inputVal the value to be put
     * @param inputRow the row of the cell
     * @param inputCol the column of the cell
     * @return if the number is valid or not
     */
    public boolean putNumber(int inputVal, int inputRow, int inputCol){
        if(!isCellEditable(inputRow, inputCol)){
            throw new IllegalArgumentException("The cell is not editable. Row: " + inputRow + " Column + " + inputCol);
        } else if (inputVal <= 0 || inputVal > 9) {
            throw new IllegalArgumentException("The input is invalid: " + inputVal);
        }

        // Return true if the value is already there
        if(workingBoard[inputRow][inputCol] == inputVal) {
            return true;
        }

        // Checks sudoku rules and assign the value if they are met
        if(checker.checkSudokuRules(workingBoard, inputVal, inputRow, inputCol)) {
            // Filling the blank cell
            if(workingBoard[inputRow][inputCol] == SudokuDB.EMPTY_CELL)
                this.remainingCells--;

            saveHistory(inputRow, inputCol);
            workingBoard[inputRow][inputCol] = inputVal;

            return true;
        }

        // Delete the number from the cell if the input is invalid.
        deleteNumber(inputRow, inputCol);
        this.mistakesCounter++;
        return false;
    }

    /**
     * Attempts to delete a number onto a current board
     * Updates the board if and only if the cell is editable
     * @param inputRow the row of the cell
     * @param inputCol the column of the cell
     */
    public void deleteNumber(int inputRow, int inputCol){
        if(!isCellEditable(inputRow, inputCol)){
            throw new IllegalArgumentException("The cell is not editable. Row: " + inputRow + " Column + " + inputCol);
        }

        if(workingBoard[inputRow][inputCol] != SudokuDB.EMPTY_CELL) {
            this.remainingCells++;
            saveHistory(inputRow, inputCol);
        }

        workingBoard[inputRow][inputCol] = SudokuDB.EMPTY_CELL;
    }

    /**
     * Used to check if the user completed the game or not.
     * @return if the Sudoku is completed or not
     */
    public boolean isCompleted() {
        return remainingCells <= 0;
    }

    /**
     * @return the set of conflicting cells if the input is invalid. Empty set if the input is valid.
     */
    public Set<SudokuCell> getConflictingCells() {
        return checker.getSudokuErrorCells().keySet();
    }

    /**
     * Checks if the sudoku cell is editable or not
     * @param inputRow the row that the user tries to input
     * @param inputCol the column that the user tries to input
     * @return if the cell can be edited or not
     */
    public boolean isCellEditable(int inputRow, int inputCol){
        if(inputRow < 0 || inputRow > SudokuCell.SUDOKU_BOARD_SIZE - 1
                || inputCol < 0 || inputCol > SudokuCell.SUDOKU_BOARD_SIZE - 1) {
            throw new IllegalArgumentException("The cell does not exist. Row: " + inputRow + " Column + " + inputCol);
        }

        return originalBoard[inputRow][inputCol] == 0;
    }

    /**
     * Deep copy the sudoku Board
     * @param sudokuBoard the board to be copied
     * @return the 2d array containing the same value as the sudokuBoard.
     */
    private int[][] copySudokuBoard(int[][] sudokuBoard) {
        int[][] newBoard = new int[sudokuBoard.length][sudokuBoard[0].length];
        for (int i = 0; i < sudokuBoard.length; i++) {
            newBoard[i] = Arrays.copyOf(sudokuBoard[i], sudokuBoard[i].length);
        }

        return newBoard;
    }

    /**
     * Save the sudoku's movement history
     * @param inputRow current row
     * @param inputCol current column
     */
    private void saveHistory(int inputRow, int inputCol) {
        int[][] currentBoard = copySudokuBoard(workingBoard);
        SudokuCell currentCell = new SudokuCell(inputRow, inputCol);
        this.history.push(new SudokuMovement(currentBoard, currentCell));
    }

    /**
     * Undo a movement. Sets the Sudoku Board to the previous movement
     * @return the focused cell coordinate when the previous movement was made.
     */
    public SudokuCell undoMovement() {
        if(!this.history.isEmpty()){
            SudokuMovement previousMovement = this.history.pop();
            this.workingBoard = copySudokuBoard(previousMovement.getSudokuBoard());
            countRemainingCells();
            return previousMovement.getFocusedCell();
        } else {
            return null;
        }
    }

    /**
     * Count the number of remaining cells in the current sudokuBoard
     */
    private void countRemainingCells() {
        int remainingCells = 0;
        for(int i = 0; i < workingBoard.length; i++) {
            for(int j = 0; j < workingBoard[i].length; j++){
                if(workingBoard[i][j] == SudokuDB.EMPTY_CELL)
                    remainingCells++;
            }
        }
        this.remainingCells = remainingCells;
    }

    public int[][] getWorkingBoard() {
        return workingBoard;
    }

    public int getRemainingCells() {
        return remainingCells;
    }

    public int getMistakesCounter() {
        return mistakesCounter;
    }
}

/**
 * Record of a history of the Sudoku Movement
 * SudokuGameLogic stores the history of SudokuMovements
 */
class SudokuMovement {
    /**
     * Sudoku board
     */
    private final int[][] sudokuBoard;

    /**
     * Focused cell when the cell is edited.
     */
    private final SudokuCell focusedCell;

    /**
     * Constructor
     *
     * @param sudokuBoard board of the sudoku at certain instant
     * @param focusedCell focused cell at certain instant
     */
    public SudokuMovement(int[][] sudokuBoard, SudokuCell focusedCell) {
        this.sudokuBoard = sudokuBoard;
        this.focusedCell = focusedCell;
    }

    public int[][] getSudokuBoard() {
        return sudokuBoard;
    }

    public SudokuCell getFocusedCell() {
        return focusedCell;
    }
}