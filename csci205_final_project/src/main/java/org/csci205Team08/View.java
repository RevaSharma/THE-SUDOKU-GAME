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
 * Package: org.csci205Team08
 * Class: View
 *
 * Description: Enumeration type that defines the state of the game
 *
 * ****************************************
 */
package org.csci205Team08;

/**
 * The Enumeration that consists of the FXML file paths for each view.
 */
public enum View {
    MENU("/views/menu.fxml"),
    SUDOKU_GAME("/views/sudokuGame.fxml");
    private final String fxmlPath;
    View(String fxmlPath) { this.fxmlPath = fxmlPath; }

    public String getFxmlPath(){ return this.fxmlPath; }
}
