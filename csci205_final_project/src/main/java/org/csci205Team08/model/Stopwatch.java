/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: 02 - 11am
 * Date: 11/28/2022
 * Time: 11:29 PM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.model
 * Class: Stopwatch
 *
 * Description: Stopwatch class used to measure the time elapsed to finish the sudoku game
 *
 * ****************************************
 */
package org.csci205Team08.model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

/**
 * Stopwatch class used to measure the time elapsed to finish the sudoku game
 */
public class Stopwatch {
    /** Duration of the timer's count: 1 second */
    private final int TIME_DURATION = 1;

    /** Elapsed time in seconds */
    private SimpleIntegerProperty elapsedTime;

    /** Timeline object */
    private Timeline timeline;

    /**
     * Initializer of the stopwatch
     */
    public Stopwatch() {
        elapsedTime = new SimpleIntegerProperty();
        this.timeline = new Timeline(
             // Defines the behavior after TIME_DURATIONS
             new KeyFrame(Duration.seconds(TIME_DURATION),
                     (event) -> elapsedTime.set(elapsedTime.get() + TIME_DURATION))
        );

        // Run the timer indefinitely
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Start the stopwatch
     */
    public void start() {
        this.timeline.play();
    }

    /**
     * Start the stopwatch from time = 0
     */
    public void initAndStart() {
        reset();
        start();
    }

    /**
     * Stop the stopwatch
     */
    public void stop() {
        this.timeline.pause();
    }

    /**
     * Reset the stopwatch
     */
    public void reset() {
        this.timeline.stop();
        this.elapsedTime.set(0);
    }

    public int getElapsedTime() {
        return elapsedTime.get();
    }

    public SimpleIntegerProperty elapsedTimeProperty() {
        return elapsedTime;
    }

    /**
     * Static function that helps to convert elapsed time in seconds to HH:MM:SS form
     * @param timeInSec elapsed time in seconds
     * @return the time in format of MM:SS or HH:MM:SS if the elapsed time is over one hour.
     */
    public static String formatConciseTime(int timeInSec) {
        int hour = timeInSec / 3600;
        int minutes = (timeInSec % 3600) / 60;
        int seconds = timeInSec % 60;

        String time;
        if(hour > 0) {
            time = String.format("%d:%02d:%02d", hour, minutes, seconds);
        } else {
            time = String.format("%02d:%02d", minutes, seconds);
        }

        return time;
    }

    /**
     * Static function that helps to convert elapsed time in seconds to HH:MM:SS form
     * @param timeInSec elapsed time in seconds
     * @return the time in format of MM:SS or HH:MM:SS if the elapsed time is over one hour.
     */
    public static String formatDetailedTime(int timeInSec) {
        int hour = timeInSec / 3600;
        int minutes = (timeInSec % 3600) / 60;
        int seconds = timeInSec % 60;

        String time;
        if(hour > 0) {
            time = String.format("%d hours %d minutes %d seconds", hour, minutes, seconds);
        } else if (minutes > 0) {
            time = String.format("%d minutes %d seconds", minutes, seconds);
        } else {
            time = String.format("%d seconds", seconds);
        }
        return time;
    }
}