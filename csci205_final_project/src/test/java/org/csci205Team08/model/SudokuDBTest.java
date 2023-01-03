/*
 * ****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: Section 2
 * Date: 11/13/22
 * Time: 5:30 PM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.model
 * Class: SudokuDBTest
 *
 * Description: Tests the functionality of the SudokuDB class
 *
 * ****************************************
 */
package org.csci205Team08.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SudokuDBTest {

    public SudokuDB sDB;

    int[][] sudokuBoardFirst = new int[][]{
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

    int[][] sudokuSolFirst = new int[][]{
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

    int[][] sudokuBoardLast = new int[][]{
            {0,6,5,3,7,0,0,0,2},
            {0,0,0,0,0,1,3,7,0},
            {0,0,0,6,4,0,8,0,0},
            {0,9,7,0,0,4,0,2,8},
            {0,8,0,0,9,0,0,0,1},
            {1,0,0,0,2,0,9,4,0},
            {0,4,0,0,0,6,7,0,0},
            {0,7,0,0,1,8,0,5,0},
            {2,3,0,9,0,0,0,6,0}
    };

    int[][] sudokuSolLast = new int[][]{
            {8,6,5,3,7,9,4,1,2},
            {9,2,4,5,8,1,3,7,6},
            {7,1,3,6,4,2,8,9,5},
            {3,9,7,1,6,4,5,2,8},
            {4,8,2,7,9,5,6,3,1},
            {1,5,6,8,2,3,9,4,7},
            {5,4,1,2,3,6,7,8,9},
            {6,7,9,4,1,8,2,5,3},
            {2,3,8,9,5,7,1,6,4}
    };

    @BeforeEach
    void setUp() {
        sDB = new SudokuDB();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readCSV() throws FileNotFoundException, URISyntaxException {
        ArrayList<int[][]> emptyList = sDB.getSudokuBoardsList();
        assertEquals(emptyList.size(), 0);
        sDB.readCSV();
        ArrayList<int[][]> boardsList = sDB.getSudokuBoardsList();
        ArrayList<int[][]> solsList = sDB.getSudokuSolutionsList();
        assertEquals(boardsList.size(), 5775);
        assertEquals(solsList.size(), 5775);
        assertTrue(Arrays.deepEquals(sudokuBoardFirst,boardsList.get(0)));
        assertTrue(Arrays.deepEquals(sudokuSolFirst, solsList.get(0)));
        assertTrue(Arrays.deepEquals(sudokuBoardLast, boardsList.get(14)));
        assertTrue(Arrays.deepEquals(sudokuSolLast, solsList.get(14)));
    }

    @Test
    void getRandomBoard() throws FileNotFoundException, URISyntaxException {
        sDB.readCSV();
        assertTrue(sDB.getRandomBoard() instanceof int[][]);
        int[][] board1 = sDB.getRandomBoard();
        int[][] board2 = sDB.getRandomBoard();
        //hypothetically could be the same, but with an extensive database most of the time boards should be different
        //if this test fails try again
        assertFalse(Arrays.deepEquals(board1, board2));
    }
}