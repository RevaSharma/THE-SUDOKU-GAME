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
 * Class: SudokuCellTest
 *
 * Description: Tests the functionality of the SudokuCell class
 *
 * ****************************************
 */
package org.csci205Team08.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the class that encapsulates the sudoku cell
 */
class SudokuCellTest {
    /**
     * Testing the illegal cell rejection
     */
    @Test
    @DisplayName("Testing the illegal cell rejection")
    void testIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SudokuCell(100,100));
        assertThrows(IllegalArgumentException.class, () -> new SudokuCell(-1,-1));
    }

    /**
     * Test if the cell encapsulates the cell correctly
     */
    @Test
    @DisplayName("Test if the cell encapsulates the cell correctly")
    void testCellGetters() {
        SudokuCell cell = new SudokuCell(5,8);
        assertEquals(5, cell.getRow());
        assertEquals(8, cell.getColumn());
    }

    /**
     * Testing the equals() method.
     */
    @Test
    void testEquals() {
        assertEquals(new SudokuCell(1, 2), new SudokuCell(1, 2));
        assertNotEquals(new SudokuCell(1,8), new SudokuCell(1,2));
    }
}