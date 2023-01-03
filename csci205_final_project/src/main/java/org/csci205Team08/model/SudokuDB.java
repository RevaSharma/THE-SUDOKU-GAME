/*
 * ****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: 02 - 11am
 * Date: 11/7/22
 * Time: 7:15 PM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.model
 * Class: SudokuDB
 *
 * Description: Takes a csv with sudoku quizzes and their solutions and reads them in to be
 * 2d 9x9 arrays stored in 2 separate array lists and can provide a random board
 *
 * ****************************************
 */
package org.csci205Team08.model;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class SudokuDB {

    /** The value stored in the empty cell */
    public static final int EMPTY_CELL = 0;

    private static Scanner scan;

    /** Array list that stores all 2D quiz arrays from the csv */
    private ArrayList<int[][]> sudokuBoardsList;

    /** Array list that stores all 2D solution arrays from the csv */
    private ArrayList<int[][]> sudokuSolutionsList;

    public SudokuDB(){
        this.sudokuBoardsList = new ArrayList<>();
        this.sudokuSolutionsList = new ArrayList<>();
    }

    /** Reads csv file and stores sudoku quizzes and solutions in 2D arrays */
    public void readCSV() throws FileNotFoundException, URISyntaxException {
        //String fileName = "/sudoku_15test.csv";
        String fileName = "/sudoku_partial.csv";

        //parsing a CSV file into Scanner class constructor
        scan = new Scanner(getClass().getResourceAsStream(fileName));
        scan.nextLine(); // reads header and discards it

        while (scan.hasNext())
        {
            int colNum = 0;
            int rowNum = 0;
            int [][] quizTempArray = new int[9][9];
            int [][] solTempArray = new int[9][9];
            String rowString = scan.nextLine();
            String[] arrStrings = rowString.split("\\s*,\\s*"); // array of length 2 holding quiz and solution string
            // Parse through the quiz and solution and split integers into a 2D array
            for (int i = 0; i < 81; i++){
                char charQuiz = arrStrings[0].charAt(i);
                char charSol = arrStrings[1].charAt(i);
                int numQuiz = Integer.parseInt(String.valueOf(charQuiz));
                int numSol = Integer.parseInt(String.valueOf(charSol));
                if (colNum == 9){
                    colNum = 0;
                    rowNum++;
                }
                quizTempArray[rowNum][colNum] = numQuiz;
                solTempArray[rowNum][colNum] = numSol;
                colNum++;
            }


            sudokuBoardsList.add(quizTempArray);
            sudokuSolutionsList.add(solTempArray);
            colNum = 0;
            rowNum = 0;
        }
    }

    /**
     * Method to retrieve random board from list of boards generated when reading csv file
     * @return 2D array of random sudoku board
     */
    public int[][] getRandomBoard(){
        int index = (int)(Math.random() * this.sudokuBoardsList.size());

        // DEBUG purpose - Prints the answer on the console
        printAnswerBoard(index);

        return this.sudokuBoardsList.get(index);
    }

    public ArrayList<int[][]> getSudokuBoardsList() {
        return sudokuBoardsList;
    }

    public ArrayList<int[][]> getSudokuSolutionsList() {
        return sudokuSolutionsList;
    }

    /**
     * Used for debug. Prints the answer board onto console.
     * @param index index of the solution board
     */
    private void printAnswerBoard(int index) {
        int[][] board = sudokuSolutionsList.get(index);

        System.out.println("Printing the answer");
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

}