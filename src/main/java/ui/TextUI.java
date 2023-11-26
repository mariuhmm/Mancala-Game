package ui;

import mancala.*;

import java.util.Scanner;

public class TextUI {
    private MancalaGame mancalaGame;
    private Scanner scanner;

    public TextUI(MancalaGame game) {
        mancalaGame = game;
        this.scanner = new Scanner(System.in);
    }

    private static GameRules chooseGameType() {
        Scanner in = new Scanner(System.in);

        System.out.println("Choose the game type:");
        System.out.println("1. Ayo");
        System.out.println("2. Kalah");

        int choice = in.nextInt();

        if (choice == 1) {
            return new AyoRules();
        } else if (choice == 2) {
            return new KalahRules();
        } else {
            System.out.println("Invalid choice. Defaulting to Ayo.");
            return new AyoRules();
        }
    }

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

                if (mancalaGame.getBoard() instanceof KalahRules) {
                    if (((KalahRules) mancalaGame.getBoard()).extraTurn()) {
                        System.out.println("Playing Kalah");
                        System.out.println("Extra turn for " + currentPlayer.getName());
                    } else {
                        // Switch players only if it's not an extra turn
                        switchPlayer();
                    }
                } else {
                    switchPlayer();
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

    private void switchPlayer() {
        if (mancalaGame.getCurrentPlayer() == mancalaGame.getPlayers().get(0)) {
            mancalaGame.setCurrentPlayer(mancalaGame.getPlayers().get(1));
        } else {
            mancalaGame.setCurrentPlayer(mancalaGame.getPlayers().get(0));
        }
    }


    private void displayBoard() {
        System.out.println(mancalaGame.getBoard().toString());
    }

    private int getPlayerInput(Player player) {
        int pitSelection;
        do {
            System.out.print("Enter the pit number to move stones (1-6 for Player One, 7-12 for Player Two): ");
            pitSelection = scanner.nextInt();
        } while (!isValidPitSelection(pitSelection, player));

        return pitSelection;
    }

    private boolean isValidPitSelection(int pitSelection, Player player) {
        if (player == mancalaGame.getPlayers().get(0)) {
            return pitSelection >= 1 && pitSelection <= 6;
        } else {
            return pitSelection >= 7 && pitSelection <= 12;
        }
    }
    private void displayWinner() {
        try {
            Player winner = mancalaGame.getWinner();
            if (winner != null) {
                System.out.println("Congratulations, " + winner.getName() + "! You win!");
            }
        } catch (Exception e) {
            System.out.println("The game is not over yet.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Mancala!");

        // Choose game type
        GameRules chosenRules = chooseGameType();

        // Create MancalaGame with chosen rules
        MancalaGame mancalaGame = new MancalaGame();
        mancalaGame.setBoard(chosenRules);

        // Create TextUI and start the game
        TextUI textUI = new TextUI(mancalaGame);
        textUI.startGame();
    }
}
