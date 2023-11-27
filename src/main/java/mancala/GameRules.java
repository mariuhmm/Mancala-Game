package mancala;
import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {
    private static final long serialVersionUID = 1L;
    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)
    private boolean isEmpty;

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }
    /**
     * Get the number of stones in a store.
     *
     * @param storeNum The number of the store.
     * @return The number of stones in the store.
     */
    public int getStoreCount(int storeNum){
        return gameBoard.getStoreCount(storeNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
   /* default */ MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    public boolean isSideEmpty(int pitNum) {
        isEmpty=true;
        if (pitNum >= 1 && pitNum <= 6) {
            for (int i = 1; i <= 6; i++) {
                if (getNumStones(i) > 0) {
                    isEmpty = false;
                    break;
                }
            }
        } else if (pitNum >= 7 && pitNum <= 12) {
            for (int i = 7; i <= 12; i++) {
                if (getNumStones(i) > 0) {
                    isEmpty = false;
                    break;
                }
            }
        }

        return isEmpty;
    }


    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    /* default */ abstract int distributeStones(int startPit);
    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    /* default */ abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(Player one, Player two) {
        // this method can be implemented in the abstract class.
        final Store storeOne = new Store();
        final Store storeTwo = new Store();
        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/

        gameBoard.setStore(storeOne, 1);
        gameBoard.setStore(storeTwo, 2);
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }
    /**
     * Print the board as a string
     */
    @Override
    public String toString() {
        // Implement toString() method logic here.
        final StringBuilder boardString = new StringBuilder();
        boardString.append("\t");
        for (int i = 12; i >= 7; i--) {
            boardString.append(gameBoard.getNumStones(i)).append("\t");
        }
        boardString.append("\n" + gameBoard.getStoreCount(2)).append("\t\t\t\t\t\t\t");
        boardString.append(gameBoard.getStoreCount(1)).append("\n\t");
        
        for (int i = 1; i <= 6; i++) {
            boardString.append(gameBoard.getNumStones(i)).append("\t");
        }
        return boardString.toString();
    }
}
