# Project Title

2-Player Mancala Game

## Description

This project is a 2-player Mancala game made in Java. The project incorporates a GUI and both Kalah and AyoAyo rules such as capturing stones, adding stones to stores and getting extra turns. 

## Getting Started

### Dependencies

* JDK

### Executing program

Clone the repository
```
git clone https://gitlab.socs.uoguelph.ca/2430F23/mahmed44/GP3.git
```

Build the code and get run command for the file path. (To run the ai code, cd into docs and then aiSolution)
```
gradle build

gradle echo
```
Then paste the run command into the terminal to run the code.

```
java -cp build/classes/java/main ui.MancalaGUI
```
or
```
java -jar build/libs/MancalaGame.jar
```

## Limitations
All methods are completed, there should be no limitations.

## Author Information

* Author: Mariam Ahmed
* Student Number: 1186739
* Contact: mahmed44@uoguelph.ca

## Development History
* 2.1 (11/26/23)
    * Fixed bugs in the GUI
    * Added JavaDocs comments
* 2.0 (11/25/23)
    * Completed the GUI
    * Completed saving/loading mechanisms for user and game
* 1.2 (11/24/23)
    * Completed and debugged both Kalah and Ayo rules classes
    * Began creating GUI
* 1.1 (11/23/23)
    * Fixed bugs in KalahRules
* 1.0 (11/22/23)
    * Implemented countable
    * Began implementing serialization
    * Created Kalah and Ayo rules classes
    * Updated TextUI for testing purposes

## Acknowledgments
The CIS2430 textbook and course notes were used to guide the development of this project.
