/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: 02 - 11am
 * Date: 11/14/2022
 * Time: 12:44 AM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.model
 * Class: SudokuModel
 *
 * Description: Model of the Sudoku Game
 *
 * ****************************************
 */
package org.csci205Team08.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Model of the Sudoku Game
 */
public class SudokuModel {
    /** {@link ObjectProperty} of the cell's status */
    private List<List<ObjectProperty<GridState>>> boardStatus;

    /** {@link SimpleStringProperty} storing the remaining cell counts. */
    private SimpleStringProperty remainingCells;

    /** {@link SimpleStringProperty} storing the remaining cell counts. */
    private SimpleStringProperty mistakesCounter;

    /** stores the focused Box Coordinate */
    private SudokuCell focusedCellCoordinate;

    /** game engine of the sudoku */
    private SudokuGameLogic gameEngine;

    /** Database that stores sudoku quizzes */
    private SudokuDB boardDB;

    /** stopwatch to measure the elapsed time to solve sudoku */
    private Stopwatch sudokuStopwatch;


    /**
     * Constructor
     * Parses the sudoku's csv file.
     */
    public SudokuModel() {
        initializeGridState();
        remainingCells = new SimpleStringProperty();
        mistakesCounter = new SimpleStringProperty();

        gameEngine = new SudokuGameLogic();
        boardDB = new SudokuDB();
        sudokuStopwatch = new Stopwatch();

        try{
            boardDB.readCSV();
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found");
        } catch (URISyntaxException e) {
            System.out.println("String cannot be passed as URI reference");
        }
    }

    /**
     * Initialize a new game with a random board
     */
    public void initNewGame() {
        // Get random board from the sudoku database and initialize the game
        gameEngine.initGame(boardDB.getRandomBoard());
        updateProperties();
    }

    /**
     * Attempt to put a number into a sudoku board
     * @param newNumber new number to be put
     * @param row row of the cell
     * @param col column of the cell
     * @return if the cell is editable or not
     */
    public boolean putNumber(int newNumber, int row, int col){
        if(gameEngine.isCellEditable(row, col)){
            resetConflictingCells(gameEngine.getConflictingCells());
            if(gameEngine.putNumber(newNumber, row, col)){
                cellStatusProperty(row, col).set(GridState.VALID_INPUT);
            } else {
                cellStatusProperty(row, col).set(GridState.INVALID_INPUT);
                highlightConflictingCells(gameEngine.getConflictingCells());
            }
            updateProperties();
            return true;
        }

        return false;
    }

    /**
     * Attempt to delete a number into a sudoku board
     * @param row row of the cell
     * @param col column of the cell
     * @return if the cell can be edited or not
     */
    public boolean deleteNumber(int row, int col){
        if(gameEngine.isCellEditable(row, col)){
            cellStatusProperty(row, col).set(GridState.SELECTED);
            gameEngine.deleteNumber(row, col);
            resetConflictingCells(gameEngine.getConflictingCells());
            updateProperties();
            return true;
        }
        return false;
    }

    /**
     * Undo a movement made on Sudoku Board
     */
    public void undoMovement() {
        SudokuCell previousCell = this.gameEngine.undoMovement();
        if(previousCell != null) {
            this.focusedCellCoordinate = previousCell;
            this.setRelevantCells(this.focusedCellCoordinate.getRow(),
                    this.focusedCellCoordinate.getColumn());
            updateProperties();
        }
    }

    /**
     * @return if the current Sudoku game is completed or not.
     */
    public boolean isGameCompleted() {
        return gameEngine.isCompleted();
    }

    /**
     * Initialize the grid state
     */
    private void initializeGridState() {
        boardStatus = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            boardStatus.add(new ArrayList<>());
            for (int j = 0; j < 9; j++) {
                boardStatus.get(i).add(new SimpleObjectProperty<>(GridState.DEFAULT));
            }
        }
    }

    /**
     * Update the label properties.
     */
    private void updateProperties() {
        remainingCells.set(String.valueOf(gameEngine.getRemainingCells()));
        mistakesCounter.set(String.valueOf(gameEngine.getMistakesCounter()));
    }

    /**
     * Updates the relevant cells based on the user's click
     * @param row row of the cell that the user clicked
     * @param col column of the cell that the user clicked
     */
    public void setRelevantCells(int row, int col) {
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                setRelevantCell(i, j, row, col);
            }
        }
    }

    /**
     * Update the cell's GridState accordingly
     * @param row row of the cell
     * @param col column of the cell
     * @param inputRow row of the input
     * @param inputCol column of the input
     */
    private void setRelevantCell(int row, int col, int inputRow, int inputCol) {
        boardStatus.get(row).get(col).setValue(GridState.DEFAULT);
        // The selected cell
        if (row == inputRow && col == inputCol) {
            boardStatus.get(row).get(col).setValue(GridState.SELECTED);
        }
        // In the same row or column
        else if (row == inputRow || col == inputCol) {
            boardStatus.get(row).get(col).setValue(GridState.RELEVANT);
        }
        // Within same 3x3 block
        else if (inputRow / 3 == row / 3 && inputCol / 3 == col / 3) {
            boardStatus.get(row).get(col).setValue(GridState.RELEVANT);
        }
        else {
            boardStatus.get(row).get(col).setValue(GridState.DEFAULT);
        }
    }

    /**
     * Highlights the GridState of conflicting cells
     * @param cells the set contains the information about the conflicting cells
     */
    private void highlightConflictingCells(Set<SudokuCell> cells) {
        cells.forEach((cell) ->
                cellStatusProperty(cell.getRow(), cell.getColumn()).set(GridState.CONFLICTING)
        );
    }

    /**
     * Resets the highlighting of the GridState of conflicting cells
     * @param cells the set contains the information about the conflicting cells
     */
    private void resetConflictingCells(Set<SudokuCell> cells) {
        cells.forEach((cell) ->
                setRelevantCell(cell.getRow(),
                        cell.getColumn(),
                        focusedCellCoordinate.getRow(),
                        focusedCellCoordinate.getColumn())
        );
    }

    public ObjectProperty<GridState> cellStatusProperty(int row, int col) {
        return this.boardStatus.get(row).get(col);
    }

    public GridState getCellStatus(int row, int col) {
        return this.boardStatus.get(row).get(col).get();
    }

    /**
     * Sets the focused box
     * @param row row of the focused box
     * @param col column of the focused box
     */
    public void setFocusedCellCoordinate(int row, int col) {
        this.focusedCellCoordinate = new SudokuCell(row, col);
    }

    /**
     * @return the row of the focused coordinate
     */
    public int getFocusedCellRow() {
        return focusedCellCoordinate.getRow();
    }

    /**
     * @return the column of the focused coordinate
     */
    public int getFocusedCellColumn() {
        return focusedCellCoordinate.getColumn();
    }

    /**
     * @return the current sudokuBoard
     */
    public int[][] getCurrentBoard() {
        return gameEngine.getWorkingBoard();
    }

    public String getRemainingCells() {
        return remainingCells.get();
    }

    public SimpleStringProperty remainingCellsProperty() {
        return remainingCells;
    }

    public String getMistakesCounter() {
        return mistakesCounter.get();
    }

    public SimpleStringProperty mistakesCounterProperty() {
        return mistakesCounter;
    }

    public Stopwatch getSudokuStopwatch() { return sudokuStopwatch; }
}