/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: 02 - 11am
 * Date: 11/7/2022
 * Time: 11:29 AM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.controllers
 * Class: SudokuGameController
 *
 * Description: Class that controls the screen of the Sudoku game GUI
 *
 * ****************************************
 */
package org.csci205Team08.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.csci205Team08.View;
import org.csci205Team08.model.GridState;
import org.csci205Team08.model.Stopwatch;
import org.csci205Team08.model.SudokuModel;

/**
 * Controller of the Sudoku Game's screen
 */
public class SudokuGameController extends AppController {
    /** Length of the sudoku board*/
    private final int SUDOKU_BOARD_LEN = 9;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /** Give up button */
    @FXML
    private Button giveupBtn;

    /** Mistake label */
    @FXML
    private Label mistakeLabel;

    /** Remaining blocks label */
    @FXML
    private Label remainingBlocksLabel;

    /** Sudoku board pane */
    @FXML
    private GridPane sudokuBoard;

    /** 81 Sudoku cell labels */
    private Label[][] sudokuLabel;

    /** Elapsed time label */
    @FXML
    private Label timeLabel;

    /** Undo button */
    @FXML
    private Button undoBtn;

    @FXML
    private Button tutorialBtn;

    /**
     * Sets the model of the controller
     * @param model model of the Sudoku
     */
    @Override
    public void setModel(SudokuModel model){
        super.setModel(model);
        initBindings();
        model.initNewGame();
        model.getSudokuStopwatch().initAndStart();
        resetSudokuBoardLabels(model.getCurrentBoard());
    }

    /**
     * Initialize the 9X9 Label on the SudokuBoard
     * Source: <a href="https://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column">https://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column</a>
     */
    private void initializeSudokuLabel() {
        this.sudokuLabel = new Label[9][9];
        for(Node childNode : this.sudokuBoard.getChildren()) {
            if (childNode instanceof Label thisLabel){
                int column = GridPane.getColumnIndex(thisLabel) == null ? 0 : GridPane.getColumnIndex(thisLabel);
                int row = GridPane.getRowIndex(thisLabel) == null ? 0 : GridPane.getRowIndex(thisLabel);
                this.sudokuLabel[row][column] = thisLabel;
                addEventHandlerOnSudokuLabel(thisLabel, row, column);
            }
        }
    }

    /**
     * Add event handler on sudoku label to update the background color of the sudoku cells
     * @param sudokuLabel the label of the sudoku's cell
     * @param row the row of the sudoku cell
     * @param column the column of the sudoku cell
     */
    private void addEventHandlerOnSudokuLabel(Label sudokuLabel, int row, int column) {
        getModel().cellStatusProperty(row, column).addListener(
                (observable, oldValue, newValue) -> {
                    sudokuLabel.getStyleClass().remove("selectedBox");
                    sudokuLabel.getStyleClass().remove("relevantBox");
                    sudokuLabel.getStyleClass().remove("validInputBox");
                    sudokuLabel.getStyleClass().remove("invalidInputBox");
                    sudokuLabel.getStyleClass().remove("conflictingBox");


                    // Delete the label if the input was invalid
                    if (oldValue == GridState.INVALID_INPUT) {
                        this.sudokuLabel[row][column].setText(" ");
                        sudokuLabel.getStyleClass().add("editableBox");
                    }

                    // Update the background color accordingly
                    if (newValue == GridState.SELECTED) {
                        sudokuLabel.getStyleClass().add("selectedBox");
                    }
                    else if (newValue == GridState.RELEVANT) {
                        sudokuLabel.getStyleClass().add("relevantBox");
                    }
                    else if (newValue == GridState.VALID_INPUT) {
                        sudokuLabel.getStyleClass().add("validInputBox");
                    }
                    else if (newValue == GridState.INVALID_INPUT) {
                        sudokuLabel.getStyleClass().remove("editableBox");
                        sudokuLabel.getStyleClass().add("invalidInputBox");
                    }
                    else if (newValue == GridState.CONFLICTING) {
                        sudokuLabel.getStyleClass().add("conflictingBox");
                    }
                });
    }

    /**
     * Initialize the controller
     */
    @FXML
    void initialize() {
        assert giveupBtn != null : "fx:id=\"giveupBtn\" was not injected: check your FXML file 'sudokuGame.fxml'.";
        assert mistakeLabel != null : "fx:id=\"mistakeLabel\" was not injected: check your FXML file 'sudokuGame.fxml'.";
        assert remainingBlocksLabel != null : "fx:id=\"remainingBlocksLabel\" was not injected: check your FXML file 'sudokuGame.fxml'.";
        assert sudokuBoard != null : "fx:id=\"sudokuBoard\" was not injected: check your FXML file 'sudokuGame.fxml'.";
        assert timeLabel != null : "fx:id=\"timeLabel\" was not injected: check your FXML file 'sudokuGame.fxml'.";
        assert undoBtn != null : "fx:id=\"undoBtn\" was not injected: check your FXML file 'sudokuGame.fxml'.";
        assert tutorialBtn != null : "fx:id=\"tutorialBtn\" was not injected: check your FXML file 'sudokuGame.fxml'.";

        initEventHandlers();
    }


    /**
     * Initialize the bindings
     */
    private void initBindings() {
        initializeSudokuLabel();
        remainingBlocksLabel.textProperty().bind(getModel().remainingCellsProperty());
        mistakeLabel.textProperty().bind(getModel().mistakesCounterProperty());

        getModel().getSudokuStopwatch().elapsedTimeProperty().addListener(
                (observable, oldValue, newValue) -> {
                    String timeRepresentation = Stopwatch.formatConciseTime(newValue.intValue());
                    timeLabel.setText(timeRepresentation);
                });
    }

    /**
     * Initialize the event handlers
     */
    private void initEventHandlers() {
        setUpUndoButton();
        setUpGiveUpButton();
        initSudokuBoardOnClick();
        initKeyBoardInput();
        setUpTutorialBtn();
    }

    /**
     * It adds an event handler to the tutorial button and when the user clicks on it,
     * A pop-up window come up with the set of rules and instructions on how to play sudoku
     */
    private void setUpTutorialBtn() {
        tutorialBtn.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sudoku Instructions");
            alert.setHeaderText("Instructions");
            alert.setContentText("""
                    Rules:\s
                    1. Each row must contain every number from 1-9\s
                    2. Each column must contain every number from 1-9\s
                    3. Each 3x3 box must contain every number from 1-9\s
                    Fill all cells without breaking the above rules!\s

                    Tips:\s
                    The cell will highlight red if you input an wrong number. The conflicting cell will also be highlighted.
                    If you put a valid number, it will be highlighted green.
                    The grey cells are relevant cells that relates to the rules above.
                    Think before putting numbers.
                    
                    Good luck.""");
            alert.show();
            sudokuBoard.requestFocus();
        });
    }

    /**
     * Set up event handlers for the undo button
     */
    private void setUpUndoButton() {
        undoBtn.setOnMouseClicked(event -> {
            getModel().undoMovement();
            resetSudokuBoardLabels(getModel().getCurrentBoard());
            sudokuBoard.requestFocus();

            undoBtn.setStyle("-fx-background-color: white");
            PauseTransition pause = new PauseTransition(Duration.seconds(0.35));
            pause.setOnFinished(event1 -> {
                undoBtn.setStyle("-fx-background-color: lightgrey");
            });
            pause.play();
        });
    }

    /**
     * Set up event handlers for the give up button
     */
    private void setUpGiveUpButton() {
        giveupBtn.setOnMouseClicked(event -> {
            giveupBtn.setStyle("-fx-background-color: white");
            PauseTransition pause = new PauseTransition(Duration.seconds(0.35));
            pause.setOnFinished(event1 -> {
                giveupBtn.setStyle("-fx-background-color: #E5C2C0");
            });
            pause.play();
        });


        // Switch the view to Level select screen and pops up a window to make sure that the user wants to give up
        /* https://stackoverflow.com/questions/43031602/how-to-set-a-method-to-a-javafx-alert-button This website helped me a lot
         * When I am implementing this particular method
         */
        giveupBtn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Give Up!");
            alert.setHeaderText("Are you sure you want to give up?");
            alert.setContentText("Click \"OK\" if you want to give up,\nClick \"Cancel\" otherwise.");

            Optional<ButtonType> checker = alert.showAndWait();
            ButtonType buttonType = checker.orElse(ButtonType.CANCEL);
            if (buttonType == ButtonType.OK){
                getApplication().switchView(View.MENU);
            }
            else {
                sudokuBoard.requestFocus();
            }
        });
    }

    /**
     * Initialize the keyboard input on the sudoku board
     */
    private void initKeyBoardInput() {
        // Request focus once the app is launched
        Platform.runLater(() -> sudokuBoard.requestFocus());
        sudokuBoard.setOnKeyPressed(event -> {
            int row = getModel().getFocusedCellRow();
            int col = getModel().getFocusedCellColumn();
            Label targetCell = sudokuLabel[row][col];
            String value = event.getText();

            // If the input is from 1 to 9 and is different from the number in the cell
            if(value.matches("^[1-9]$") && !value.equals(targetCell.getText())) {
                int newNumber = Integer.parseInt(value);
                if(getModel().putNumber(newNumber, row, col)) {
                    targetCell.setText(value);
                    checkCompletion();
                }
            }
            // Referenced https://stackoverflow.com/questions/49564002/keycode-event-for-backspace-in-javafx
            else if(event.getCode() == KeyCode.BACK_SPACE) {
                if(getModel().deleteNumber(row, col))
                    targetCell.setText(" ");
            }

        });
    }

    /**
     * Initialize the behavior of Sudoku board being clicked
     */
    private void initSudokuBoardOnClick() {
        /*
         * Got help from this website :
         * https://stackoverflow.com/questions/39178981/accessing-the-column-and-row-index-of-gridpane-in-javafx-keeps-returning-null
         */
        sudokuBoard.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            if (target != sudokuBoard){
                Node parent;
                while ((parent = target.getParent()) != sudokuBoard){
                    target = parent;
                }
            }

            if(target instanceof Label){
                int colIndex = GridPane.getColumnIndex(target) == null ? 0 : GridPane.getColumnIndex(target);
                int rowIndex = GridPane.getRowIndex(target) == null ? 0 : GridPane.getRowIndex(target);
                getModel().setFocusedCellCoordinate(rowIndex, colIndex);
                getModel().setRelevantCells(rowIndex, colIndex);
            }
        });
    }

    /**
     * Sets the label of sudoku board
     * Only used for reset/initialization purposes.
     * @param sudokuArray the array of the sudoku board
     */
    private void resetSudokuBoardLabels(int[][] sudokuArray) {
        for (int i = 0; i < SUDOKU_BOARD_LEN; i++) {
            for (int j = 0; j < SUDOKU_BOARD_LEN; j++) {
                String label;
                if (sudokuArray[i][j] == 0) {
                    label = " ";
                    sudokuLabel[i][j].getStyleClass().add("editableBox");
                } else {
                    label = String.valueOf(sudokuArray[i][j]);
                }

                sudokuLabel[i][j].setText(label);
            }
        }
    }

    /**
     * Check for the completion and display the completion popup if the sudoku is completed.
     */
    private void checkCompletion() {
        if (getModel().isGameCompleted()) {
            getModel().getSudokuStopwatch().stop();
            showCompletionPopup();
        }
    }

    /**
     * Display completion popup
     */
    private void showCompletionPopup() {
        String message = String.format("You took %s to complete. %nYou made %s mistakes.",
                Stopwatch.formatDetailedTime(getModel().getSudokuStopwatch().getElapsedTime()),
                getModel().getMistakesCounter());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, new ButtonType("Go back to Menu"));
        alert.setTitle("Congratulations!!");
        alert.setHeaderText("Congratulations!! You completed the Sudoku game!");

        // Block other threads until this alert is closed
        alert.showAndWait();

        // Go back to the menu view
        getApplication().switchView(View.MENU);
    }
}
