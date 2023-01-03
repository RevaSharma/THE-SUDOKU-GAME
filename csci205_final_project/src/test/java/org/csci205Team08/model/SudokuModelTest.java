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
 * Class: SudokuModelTest
 *
 * Description: Tests the functionality of the SudokuModel class
 *
 * ****************************************
 */
package org.csci205Team08.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the SudokuModel class.
 * It does not test the methods related to the actual input
 * such as {@code putNumber()}, {@code deleteNumber()}, {@code undoMovement()},
 * since it relies on {@link SudokuGameLogic} for the underlying logic
 * and {@link SudokuModel} only updates the {@link javafx.beans.property.SimpleStringProperty}
 * and {@link javafx.beans.property.ObjectProperty}.
 */
class SudokuModelTest {

    /** {@link SudokuModel} object */
    private SudokuModel model;

    /** Abbreviation of GridState.RELEVANT */
    private final GridState R = GridState.RELEVANT;

    /** Abbreviation of GridState.DEFAULT */
    private final GridState D = GridState.DEFAULT;

    /** Abbreviation of GridState.SELECTED */
    private final GridState S = GridState.SELECTED;


    @BeforeEach
    void setUp() {
        model = new SudokuModel();
    }

    /**
     * Checks the initialization of new game.
     */
    @Test
    void initNewGame() {
        model.initNewGame();
        assertEquals("0", model.getMistakesCounter());
        assertNotEquals("0", model.getRemainingCells());
    }

    /**
     * Check if the model identifies the relevant cells correctly
     */
    @Test
    void setRelevantCells() {
        model.setRelevantCells(1,1);
        final GridState[][] gridState1 = new GridState[][]{
                {R,R,R,D,D,D,D,D,D},
                {R,S,R,R,R,R,R,R,R},
                {R,R,R,D,D,D,D,D,D},
                {D,R,D,D,D,D,D,D,D},
                {D,R,D,D,D,D,D,D,D},
                {D,R,D,D,D,D,D,D,D},
                {D,R,D,D,D,D,D,D,D},
                {D,R,D,D,D,D,D,D,D},
                {D,R,D,D,D,D,D,D,D}
        };

        assertTrue(isGridStateEqual(gridState1, model));

        model.setRelevantCells(5,5);
        final GridState[][] gridState2 = new GridState[][]{
                {D,D,D,D,D,R,D,D,D},
                {D,D,D,D,D,R,D,D,D},
                {D,D,D,D,D,R,D,D,D},
                {D,D,D,R,R,R,D,D,D},
                {D,D,D,R,R,R,D,D,D},
                {R,R,R,R,R,S,R,R,R},
                {D,D,D,D,D,R,D,D,D},
                {D,D,D,D,D,R,D,D,D},
                {D,D,D,D,D,R,D,D,D}
        };

        assertTrue(isGridStateEqual(gridState2, model));
    }

    /**
     * Helper function to compare two boards' {@link GridState}
     * @param expected expected Sudoku GridStates
     * @param model {@link SudokuModel} object
     * @return if they are equivalent or not
     */
    private boolean isGridStateEqual(GridState[][] expected, SudokuModel model) {
        for(int i = 0; i < expected.length; i++) {
            for(int j = 0; j < expected[i].length; j++) {
                if(expected[i][j] != model.getCellStatus(i, j))
                    return false;
            }
        }

        return true;
    }
}