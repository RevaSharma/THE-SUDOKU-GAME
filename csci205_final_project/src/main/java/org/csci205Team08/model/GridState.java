/*
 * ****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: Section 2
 * Date: 11/7/22
 * Time: 7:15 PM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.model
 * Class: GridState
 *
 * Description: Enumeration type that defines the state of a grid
 *
 * ****************************************
 */
package org.csci205Team08.model;

/**
 * Defines the state of a grid
 */
public enum GridState {
    DEFAULT, // Empty cell
    SELECTED, // Selected cell
    RELEVANT, // Relevant cells
    VALID_INPUT, // Valid input
    INVALID_INPUT, // Invalid input
    CONFLICTING // Conflicting cell
}
