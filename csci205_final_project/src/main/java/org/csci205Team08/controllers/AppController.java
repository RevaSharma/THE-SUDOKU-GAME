/*
 * ****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: Section 2
 * Date: 12/5/22
 * Time: 11:30 AM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.controllers
 * Class: AppController
 *
 * Description: Controllers that extends this class can refer to the main JavaFX application
 * used for the controllers to access the main app to change the views.
 *
 * ****************************************
 */
package org.csci205Team08.controllers;

import org.csci205Team08.SudokuMain;
import org.csci205Team08.model.SudokuModel;

/**
 * Controllers that extends this class can refer to the main JavaFX application
 * used for the controllers to access the main app to change the views.
 */
public abstract class AppController {
    /** Reference to the application */
    private SudokuMain app;

    /** Reference to the model */
    private SudokuModel model;

    /**
     * Sets the reference to the JavaFX application
     * @param app The application
     */
    public void setApplication(SudokuMain app){
        this.app = app;
    }

    /**
     * Sets the model of the controller
     * @param model model of the Sudoku
     */
    public void setModel(SudokuModel model) {
        this.model = model;
    }

    protected SudokuMain getApplication() {
        return app;
    }

    protected SudokuModel getModel() {
        return model;
    }
}
