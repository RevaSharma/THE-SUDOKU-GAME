# CSCI 205 - Software Engineering and Design - Final Project Team 8
Bucknell University
Lewisburg, PA

Instructor: Dr. Brian King

Client: Brandon Burkett

Semester: Fall 2022

## Team Information

### Team 8

Scrum Master: Tsugunobu Miyake, Sophomore, Computer Science and Engineering Student.

Product Owner: Kit Jackson, Junior Computer Science and Engineering Student.

Developer: Reva Sharma, Sophomore Computer Science and Engineering Student.

Developer: Doruk Ozar, Junior Computer Science and Engineering Student.

## Project Information


### Summary
Our project is a GUI based version of this popular game Sudoku. The main objective of this project was the design, implementation and evaluation of this application that allows users to solve Sudoku games and potentially request for hints. 
Sudoku puzzles can be solved with the resultant app. Users of the app can solve a puzzle by inputting numbers and highlighting cells. Additionally, undo and delete functionalities are also a part of the game, and cells that were filled improperly are highlighted.

We started our game logic by finding sudoku puzzles from an online website in the form of a CSV file. Then we read the CSV files and combined them with the user interface that we have developed. 
We have also made an algorithm where it picks a random sudoku puzzle every time we click on the play button from the database and displays it on the UI. We also have implemented an algorithm where we check the user input so that we can check if it is a valid input or not. 
If the input is not valid, we color the number that is inputted and also the cell red. If it is valid then we color it to blue so that the user won’t confuse himself/herself with the given numbers at the beginning of the game. We have to implement an enumeration class where we have the states of the cells. 
It can have 6 different states: Default, Selected, Relevant, Valid Input, Invalid Input, and Conflicting. When the state of a cell is Default the user cannot change the number in that cell. If the state is Selected, that means that the cell is selected and all the row and column and the 3x3 square that the cell is in becomes a Relevant state so we color those to gray so that the user can see clearly where they are inputting.


### Package Structure
csci205FinalProject
README.md
src/main/java/org.csci205Team08/controllers contains all the Controller Classes, /model contains all the Model Classes, SudokuMain, View
src/main/resources contains our product logo, and CSV files containing the 60000 Sudoku Quizzes we are reading in
src/test/java/org.csci205Team08.model contains all our JUnit Tests
design/ contains ClassDiagram.pdf, CRCCards.pdf, UseCase.pdf
docs/ contains DesignManual.pdf, ScrumReport.pdf (Generate by AIECode at end of Project Review), UserManual.pdf, Presentation.ppt

### 3rd Party Libraries
JavaFX

### How to run it
The user only interacts with the JavaFX scene which gets generated when the application is being run. This game is made for users who have played Sudoku before and also for new players who can get familiar with the game through the inbuilt tutorial option. 
In order to play the game, select the SudokuMain class (To find, use package structure: src/main/java/org.csci205Team08/SudokuMain) and run the program.

## Video Presentation
