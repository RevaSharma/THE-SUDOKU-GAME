/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: 02 - 11am
 * Date: 11/7/2022
 * Time: 5:05 PM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.controllers
 * Class: menuController
 *
 * Description: Controller for the menu screen which is the opening screen when launching the app
 *
 * ****************************************
 */
package org.csci205Team08.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.csci205Team08.View;


/**
 * Controller for the menu screen
 * The first screen after launching the app
 */
public class MenuController extends AppController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /** The logo's image */
    @FXML
    private ImageView logo;

    /** Menu Pane that has all pieces */
    @FXML
    private AnchorPane rootForMenu;

    /** The exit button that closes the app */
    @FXML
    private Button exitBtn;

    /** The play button that proceeds to the game */
    @FXML
    private Button playBtn;

    /**
     * Initialize the controller
     */
    @FXML
    void initialize() {
        assert exitBtn != null : "fx:id=\"exitBtn\" was not injected: check your FXML file 'menu.fxml'.";
        assert playBtn != null : "fx:id=\"playBtn\" was not injected: check your FXML file 'menu.fxml'.";
        assert logo != null : "fx:id=\"logo\" was not injected: check your FXML file 'menu.fxml'.";
        assert rootForMenu != null : "fx:id=\"rootForMenu\" was not injected: check your FXML file 'menu.fxml'.";

        initEventHandlers();
        initBindings();
    }

    /**
     * Initialize the button events
     */
    private void initEventHandlers() {
        playBtn.setOnAction(event -> getApplication().switchView(View.SUDOKU_GAME));
        exitBtn.setOnAction(event -> Platform.exit());
    }

    /**
     * Initialize the bindings
     * It binds the logo to the Anchor Pane so that it can resize when the user resize the window
     */
    private void initBindings(){
        logo.fitWidthProperty().bind(rootForMenu.widthProperty().divide(2));
        logo.fitHeightProperty().bind(rootForMenu.heightProperty().divide(2));
    }
}
