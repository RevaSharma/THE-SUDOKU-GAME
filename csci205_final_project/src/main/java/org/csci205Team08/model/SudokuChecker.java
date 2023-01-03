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
 * Class: SudokuChecker
 *
 * Description: Class that checks a possible user input against the rules of sudoku and the rest of the board
 * There is an internal enumeration type ErrorType that defines the type of error
 *
 * ****************************************
 */
package org.csci205Team08.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Type of the Sudoku error
 */
enum ErrorType {
    ILLEGAL_ROW,
    ILLEGAL_COLUMN,
    ILLEGAL_BOX
}

/**
 * Sudoku checker that checks the sudoku board
 */
public class SudokuChecker {

    /** the sudoku board to be inspected */
    private int[][] sudokuBoard;


    /**
     * Map that stores the sudoku cell and error type
     */
    private Map<SudokuCell, ErrorType> sudokuErrorCells;

    public Map<SudokuCell, ErrorType> getSudokuErrorCells() {
        return sudokuErrorCells;
    }

    /**
     * Constructor
     */
    public SudokuChecker(){
        this.sudokuErrorCells = new HashMap<>();
    }

    /**
     * Sets the new sudoku board
     * Resets the error cells
     * @param sudokuBoard new Sudoku board to be inspected.
     */
    public void setSudokuBoard(int[][] sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        this.sudokuErrorCells.clear();
    }

    /**
     * Checks whether it will be legal to assign number to the given row, col
     * @param sudokuBoard, copy of the sudoku board array
     * @param inputVal, the number inputted by the player
     * @param inputRow, the row to be checked
     * @param inputCol, the column to be checked
     * @return true if number can be assigned to the given row & col, otherwise false
     */
    public boolean checkSudokuRules(int[][] sudokuBoard, int inputVal, int inputRow, int inputCol){
        setSudokuBoard(sudokuBoard);

        // Run all checks even if it fails on the first one. Checks for multiple errors.
        boolean passed = checkRow(inputVal, inputRow);
        passed = checkCol(inputVal, inputCol) && passed;
        passed = checkGrid(inputVal, inputRow, inputCol) && passed;

        return passed;
    }

    /**
     * Check if we find the inputted number in a given row
     * Stores the error cell if there is one
     * @param inputVal, the number inputted by the player
     * @param inputRow, the row to be checked
     * @return true if number is not present in the row, otherwise false
     */
    public boolean checkRow(int inputVal, int inputRow){
        for (int i = 0; i < 9; i++){
            if (this.sudokuBoard[inputRow][i] == inputVal){
                // Add the sudoku cell and its error type
                sudokuErrorCells.put(new SudokuCell(inputRow, i), ErrorType.ILLEGAL_ROW);
                return false;
            }
        }
        return true;
    }

    /**
     * Check if we find the inputted number in a given column
     * Stores the error cell if there is one
     * @param inputVal, the number inputted by the player
     * @param inputCol, the column to be checked
     * @return true if number is not present in the column, otherwise false
     */
    public boolean checkCol(int inputVal, int inputCol){
        for (int i = 0; i < 9; i++){
            if (this.sudokuBoard[i][inputCol] == inputVal){
                sudokuErrorCells.put(new SudokuCell(i, inputCol), ErrorType.ILLEGAL_COLUMN);
                return false;
            }
        }
        return true;
    }

    /**
     * Check if we find the same number in the particular 3*3 matrix
     * Stores the error cell if there is one
     * @param inputVal, the number inputted by the player
     * @param inputRow, the row to be checked
     * @param inputCol, the column to be checked
     * @return true if number is not present in the row and column, otherwise false
     */
    public boolean checkGrid(int inputVal, int inputRow, int inputCol){

        int sqrt = (int)Math.sqrt(this.sudokuBoard.length);
        int sRow = inputRow-inputRow%sqrt;
        int sCol = inputCol-inputCol%sqrt;
        for(int i=sRow; i<sRow + sqrt; i++)
            for(int j=sCol; j<sCol + sqrt; j++) {
                if (this.sudokuBoard[i][j] == inputVal){
                    sudokuErrorCells.put(new SudokuCell(i, j), ErrorType.ILLEGAL_BOX);
                    return false;
                }
            }

        return true;
    }
}