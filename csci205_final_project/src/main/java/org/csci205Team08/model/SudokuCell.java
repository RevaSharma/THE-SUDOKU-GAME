/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: 02 - 11am
 * Date: 11/19/2022
 * Time: 4:27 PM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.model
 * Class: SudokuCell
 *
 * Description: Class that describes the Sudoku Cell
 *
 * ****************************************
 */
package org.csci205Team08.model;

/**
 * Helper class that encapsulates the cell
 */
class SudokuCell {

    /** Size of the sudoku board */
    public final static int SUDOKU_BOARD_SIZE = 9;

    /** Row of the sudoku cell */
    private final int row;

    /** Column of the sudoku cell */
    private final int column;

    /**
     * Constructor
     */
    public SudokuCell(int row, int column){
        if(row < 0 || row > SUDOKU_BOARD_SIZE - 1 || column < 0 || column > SUDOKU_BOARD_SIZE - 1){
            throw new IllegalArgumentException("Invalid row or column. Row: " + row + " Column + " + column);
        }
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    /**
     * Two {@link SudokuCell}s are equal if they have same row and column value.
     * @param o {@link SudokuCell} to compare with
     * @return if they are equivalent or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SudokuCell that = (SudokuCell) o;

        if (row != that.row) return false;
        return column == that.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }
}