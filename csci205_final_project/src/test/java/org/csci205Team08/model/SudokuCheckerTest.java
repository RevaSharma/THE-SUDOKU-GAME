/*
 * ****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: Section 2
 * Date: 11/29/22
 * Time: 5:30 PM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.model
 * Class: SudokuCheckerTest
 *
 * Description: Tests the functionality of the SudokuChecker class
 *
 * ****************************************
 */
package org.csci205Team08.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the {@link SudokuChecker} that checks if a sudoku board satisfies the rule of sudoku.
 */
class SudokuCheckerTest {

    /** {@link SudokuChecker} object */
    public SudokuChecker sudokuCheck;

    /** sample sudoku board */
    int[][] sudokuBoard = new int[][]{
            { 3, 0, 5, 4, 0, 2, 0, 6, 0 },
            { 4, 9, 0, 7, 6, 0, 1, 0, 8 },
            { 6, 0, 0, 1, 0, 3, 2, 4, 5 },
            { 0, 0, 3, 9, 0, 0, 5, 8, 0 },
            { 9, 6, 0, 0, 5, 8, 7, 0, 3 },
            { 0, 8, 1, 3, 0, 4, 0, 9, 2 },
            { 0, 5, 0, 6, 0, 1, 4, 0, 0 },
            { 2, 0, 0, 5, 4, 9, 0, 7, 0 },
            { 1, 4, 9, 0, 0, 7, 3, 0, 6 }
    };

    /**
     * Initializing the {@link SudokuChecker}
     */
    @BeforeEach
    void setUp() {
        sudokuCheck = new SudokuChecker();
        sudokuCheck.setSudokuBoard(sudokuBoard);
        assertTrue(sudokuCheck.getSudokuErrorCells().isEmpty());
    }

    /**
     * Tests if it checks rows correctly
     */
    @Test
    void checkRow() {
        assertTrue(sudokuCheck.checkRow(9, 0));
        assertFalse(sudokuCheck.checkRow(6, 0));
        assertTrue(sudokuCheck.checkRow( 1, 7));

        Map<SudokuCell, ErrorType> errorMap = sudokuCheck.getSudokuErrorCells();
        assertEquals(1, errorMap.size()); // One error
        assertTrue(errorMap.containsKey(new SudokuCell(0,7))); // Error cell
        assertEquals(ErrorType.ILLEGAL_ROW, errorMap.get(new SudokuCell(0, 7))); // Error type
    }

    /**
     * Tests if it checks columns correctly
     */
    @Test
    void checkCol() {
        assertTrue(sudokuCheck.checkCol(5, 0));
        assertFalse(sudokuCheck.checkCol( 9, 0));

        Map<SudokuCell, ErrorType> errorMap = sudokuCheck.getSudokuErrorCells();
        assertEquals(1, errorMap.size()); // One error
        assertTrue(errorMap.containsKey(new SudokuCell(4,0))); // Error cell
        assertEquals(ErrorType.ILLEGAL_COLUMN, errorMap.get(new SudokuCell(4, 0))); // Error type
    }

    /**
     * Tests if it checks 3x3 grid correctly
     */
    @Test
    void checkGrid() {
        assertTrue(sudokuCheck.checkGrid( 1, 0,1));
        assertFalse(sudokuCheck.checkGrid( 3, 0,1));

        Map<SudokuCell, ErrorType> errorMap = sudokuCheck.getSudokuErrorCells();
        assertEquals(1, errorMap.size()); // One error
        assertTrue(errorMap.containsKey(new SudokuCell(0,0))); // Error cell
        assertEquals(ErrorType.ILLEGAL_BOX, errorMap.get(new SudokuCell(0, 0))); // Error type
    }

    /**
     * Tests with multiple errors.
     * Checks if {@link SudokuChecker} stores all conflicting cells.
     */
    @Test
    void checkMultipleErrors() {
        assertTrue(sudokuCheck.checkSudokuRules(sudokuBoard,8, 2, 4));
        assertFalse(sudokuCheck.checkSudokuRules(sudokuBoard,6, 2, 3));

        Map<SudokuCell, ErrorType> errorMap = sudokuCheck.getSudokuErrorCells();
        assertEquals(3, errorMap.size()); // There are three errors

        // Their locations
        assertTrue(errorMap.containsKey(new SudokuCell(2,0)));
        assertTrue(errorMap.containsKey(new SudokuCell(1,4)));
        assertTrue(errorMap.containsKey(new SudokuCell(6,3)));

        // Their error types
        assertEquals(ErrorType.ILLEGAL_ROW, errorMap.get(new SudokuCell(2, 0))); // Error type
        assertEquals(ErrorType.ILLEGAL_BOX, errorMap.get(new SudokuCell(1, 4))); // Error type
        assertEquals(ErrorType.ILLEGAL_COLUMN, errorMap.get(new SudokuCell(6, 3))); // Error type

    }
}