/*
 * ****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: Section 2
 * Date: 11/7/22
 * Time: 8:00 PM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08
 * Class: SudokuMain
 *
 * Description: The main class that runs the sudoku game
 *
 * ****************************************
 */
package org.csci205Team08;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.csci205Team08.controllers.AppController;
import org.csci205Team08.model.SudokuModel;

import java.io.IOException;

/**
 * The main JavaFX application
 */
public class SudokuMain extends Application {

    /** Reference to the {@link Stage} */
    private Stage primaryStage;

    /** model of the sudoku game */
    private SudokuModel model;

    /**
     * Main method
     * @param args command line argument
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the model
     */
    @Override
    public void init() {
        this.model = new SudokuModel();
    }

    /**
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        this.primaryStage.setTitle("Sudoku");

        switchView(View.MENU);

        this.primaryStage.show();
    }

    /**
     * Switches the view
     * @param nextView the next {@link View}
     */
    public void switchView(View nextView){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(nextView.getFxmlPath()));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            this.primaryStage.setScene(scene);

            AppController controller = loader.getController();
            controller.setApplication(this);
            controller.setModel(this.model);
        }catch(IOException e) {
            System.err.println("The FXML file was not loaded.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
