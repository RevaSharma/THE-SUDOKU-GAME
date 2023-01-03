/*
 * ****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: Section 2
 * Date: 12/1/22
 * Time: 11:30 AM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.model
 * Class: SudokuGameLogicTest
 *
 * Description: Tests the functionality of the SudokuGameLogic class
 *
 * ****************************************
 */
package org.csci205Team08.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the {@link SudokuGameLogic} class, a game engine of Sudoku.
 */
class SudokuGameLogicTest {

    /** {@link SudokuGameLogic} object */
    private SudokuGameLogic gameEngine;

    /** sample sudoku board */
    private final int[][] sampleProblem = new int[][]{
            {0,0,4,3,0,0,2,0,9},
            {0,0,5,0,0,9,0,0,1},
            {0,7,0,0,6,0,0,4,3},
            {0,0,6,0,0,2,0,8,7},
            {1,9,0,0,0,7,4,0,0},
            {0,5,0,0,8,3,0,0,0},
            {6,0,0,0,0,0,1,0,5},
            {0,0,3,5,0,8,6,9,0},
            {0,4,2,9,1,0,3,0,0}
    };

    /** Completed sudoku board */
    private final int[][] completedBoard = new int[][]{
            {8,6,4,3,7,1,2,5,9},
            {3,2,5,8,4,9,7,6,1},
            {9,7,1,2,6,5,8,4,3},
            {4,3,6,1,9,2,5,8,7},
            {1,9,8,6,5,7,4,3,2},
            {2,5,7,4,8,3,9,1,6},
            {6,8,9,7,3,4,1,2,5},
            {7,1,3,5,2,8,6,9,4},
            {5,4,2,9,1,6,3,7,8}
    };

    /** Number of empty cells at the beginning */
    private final int EMPTY_CELLS = 46;

    /** Partial sudoku board. Cells (0, 0), (0, 1), and (6, 4) are filled. */
    private final int[][] partialBoard = new int[][]{
        {8,6,4,3,0,0,2,0,9},
        {0,0,5,0,0,9,0,0,1},
        {0,7,0,0,6,0,0,4,3},
        {0,0,6,0,0,2,0,8,7},
        {1,9,0,0,0,7,4,0,0},
        {0,5,0,0,8,3,0,0,0},
        {6,0,0,0,3,0,1,0,5},
        {0,0,3,5,0,8,6,9,0},
        {0,4,2,9,1,0,3,0,0}
    };

    /**
     * Test the initialization of game engine
     */
    @BeforeEach
    void setUp() {
        gameEngine = new SudokuGameLogic();
        gameEngine.initGame(sampleProblem);

        // 46 empty cells to begin
        assertEquals(EMPTY_CELLS, gameEngine.getRemainingCells());

        // 0 mistakes at first
        assertEquals(0, gameEngine.getMistakesCounter());
    }

    /**
     * Tests if the game engine handles user putting numbers.
     */
    @Test
    void putNumber() {
        // This is a valid move. 45 cells remaining.
        assertTrue(gameEngine.putNumber(8,0,0));
        assertEquals(EMPTY_CELLS - 1, gameEngine.getRemainingCells());
        assertEquals(0, gameEngine.getMistakesCounter());

        // This is also a valid move. 44 cells remaining.
        assertTrue(gameEngine.putNumber(3,6,4));
        assertEquals(EMPTY_CELLS - 2, gameEngine.getRemainingCells());
        assertEquals(0, gameEngine.getMistakesCounter());

        // Putting number into a cell that already has the value
        assertTrue(gameEngine.putNumber(8,0,0));
        assertEquals(EMPTY_CELLS - 2, gameEngine.getRemainingCells());
        assertEquals(0, gameEngine.getMistakesCounter());

        // Invalid input
        assertFalse(gameEngine.putNumber(8,1,0));
        assertEquals(EMPTY_CELLS - 2, gameEngine.getRemainingCells());
        assertEquals(1, gameEngine.getMistakesCounter());

        // Trying to edit the uneditable cell (part of the question)
        assertThrows(IllegalArgumentException.class, () -> gameEngine.putNumber(9, 0, 2));

        // Trying to put the cell illegal value = 100
        assertThrows(IllegalArgumentException.class, () -> gameEngine.putNumber(0, 0, 100));
    }

    /**
     * Tests if the game engine deletes the user's input correctly
     */
    @Test
    void deleteNumber() {
        gameEngine.putNumber(8,0,0);
        gameEngine.putNumber(3,6,4);
        gameEngine.putNumber(6,0,1);

        assertEquals(EMPTY_CELLS - 3, gameEngine.getRemainingCells());

        // Deleting numbers
        gameEngine.deleteNumber(0,0);
        assertEquals(EMPTY_CELLS - 2, gameEngine.getRemainingCells());

        gameEngine.deleteNumber(0,1);
        assertEquals(EMPTY_CELLS - 1, gameEngine.getRemainingCells());

        gameEngine.deleteNumber(0,1);
        assertEquals(EMPTY_CELLS - 1, gameEngine.getRemainingCells());

        // Trying to delete the question.
        assertThrows(IllegalArgumentException.class, () -> gameEngine.deleteNumber(0,2));
    }

    /**
     * Tests if the game engine detects the completion of Sudoku.
     */
    @Test
    void isCompleted() {
        assertFalse(gameEngine.isCompleted());

        SudokuGameLogic completedGame = new SudokuGameLogic();
        completedGame.initGame(completedBoard);
        assertTrue(completedGame.isCompleted());
    }

    /**
     * Tests if the game engine retrieves correct set of cells that conflict with the input
     */
    @Test
    void getConflictingCells() {
        // Invalid input
        assertFalse(gameEngine.putNumber(9,1,7));
        Set<SudokuCell> conflictingCells = new HashSet<>();

        // Cells that conflicts with the input
        conflictingCells.add(new SudokuCell(1,5));
        conflictingCells.add(new SudokuCell(0,8));
        conflictingCells.add(new SudokuCell(7,7));

        assertEquals(conflictingCells, gameEngine.getConflictingCells());
    }

    /**
     * Tests if the game engine recognizes editable cells
     */
    @Test
    void isCellEditable() {
        // Cell (0,0) can be edited
        assertTrue(gameEngine.isCellEditable(0,0));

        // Cell (6,8) can be edited
        assertFalse(gameEngine.isCellEditable(6,8));
    }

    /**
     * Tests if undo functionality works as intended.
     */
    @Test
    void undoMovement() {
        gameEngine.putNumber(8,0,0);
        gameEngine.putNumber(3,6,4);
        gameEngine.putNumber(6,0,1);

        // Put a number and undo
        gameEngine.putNumber(5,0,7);
        SudokuCell focusedCell1 = gameEngine.undoMovement();

        // Focused cell was (0, 7). Check if the board is expected.
        assertEquals(new SudokuCell(0,7), focusedCell1);
        assertTrue(areBoardsEqual(partialBoard, gameEngine.getWorkingBoard()));

        // Delete a number and undo
        gameEngine.deleteNumber(0,0);
        SudokuCell focusedCell2 = gameEngine.undoMovement();

        // Focused cell was (0, 0). Check if the board is expected.
        assertEquals(new SudokuCell(0,0), focusedCell2);
        assertTrue(areBoardsEqual(partialBoard, gameEngine.getWorkingBoard()));
    }

    /**
     * Tests if the game engine stores sudoku board correctly.
     */
    @Test
    void getWorkingBoard() {
        int[][] currentBoard = gameEngine.getWorkingBoard();
        assertTrue(areBoardsEqual(sampleProblem, currentBoard));

        gameEngine.putNumber(8,0,0);
        gameEngine.putNumber(3,6,4);
        gameEngine.putNumber(6,0,1);

        // Check the board after the movement
        assertTrue(areBoardsEqual(partialBoard, currentBoard));
    }

    /**
     * Helper function to compare two boards
     * @param board1 a sudoku board
     * @param board2 another sudoku board
     * @return if they are equivalent or not
     */
    private boolean areBoardsEqual(int[][] board1, int[][] board2) {
        for(int i = 0; i < board1.length; i++) {
            for(int j = 0; j < board1[i].length; j++) {
                if(board1[i][j] != board2[i][j])
                    return false;
            }
        }

        return true;
    }
}