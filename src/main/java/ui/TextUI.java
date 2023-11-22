package ui; 

import mancala.MancalaGame;
import mancala.Player;
import mancala.GameNotOverException;
import mancala.InvalidMoveException;

import java.util.Scanner;

public class TextUI {
    private MancalaGame mancalaGame;
    private Scanner scanner;

    // constructor
    public TextUI(MancalaGame game) {
        mancalaGame = game;
        this.scanner = new Scanner(System.in);
    }
    
    // method to start the game
    private void startGame() {
        System.out.println("Let's Play Mancala:");
        System.out.println("Players: " + mancalaGame.getPlayers().get(0).getName()
                + " vs. " + mancalaGame.getPlayers().get(1).getName());

        while (!mancalaGame.isGameOver()) {
            displayBoard();
            Player currentPlayer = mancalaGame.getCurrentPlayer();
            System.out.println("Current player: " + currentPlayer.getName());

            int pitSelection = getPlayerInput(currentPlayer);

            try {
                int stonesRemaining = mancalaGame.move(pitSelection);
                // check for extra turn
                if (mancalaGame.getBoard().extraTurn()) {
                    System.out.println("Extra turn for " + currentPlayer.getName());
                } else {
                    // if flag is not set, switch player
                    if(mancalaGame.getCurrentPlayer() == mancalaGame.getPlayers().get(0)){
                        mancalaGame.setCurrentPlayer(mancalaGame.getPlayers().get(1));
                    }else{
                        mancalaGame.setCurrentPlayer(mancalaGame.getPlayers().get(0));
                    }
                }

                if (stonesRemaining == 0) {
                    mancalaGame.isGameOver();
                }
            } catch (InvalidMoveException e) {
                System.out.println("Invalid move. Please try again.");

            }
        }
        displayBoard();
        displayWinner();
        
    }

    // method to display the board
    private void displayBoard() {
        System.out.println(mancalaGame.getBoard().toString());
    }

    // method to take user input
    private int getPlayerInput(Player player) {
        while (true) {
            System.out.print("Enter the pit number to move stones (1-6 for Player One, 7-12 for Player Two): ");
            int pitSelection = scanner.nextInt();
        
            return pitSelection;
        }
    }

    // method to display the winner
    private void displayWinner() {
        try{
            // get winner
            Player winner = mancalaGame.getWinner();
            if (winner != null) {
                System.out.println("Congratulations, " + winner.getName() + "! You win!");
            }
        }catch (GameNotOverException e) {
                System.out.println("The game is not over yet ");
        }
    }

    // main method
    public static void main(String[] args) {
        MancalaGame mancalaGame = new MancalaGame();
        TextUI textUI = new TextUI(mancalaGame);
        textUI.startGame();
    }
}
