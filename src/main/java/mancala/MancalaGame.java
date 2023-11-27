package mancala;

import java.util.ArrayList;
import java.io.Serializable;

public class MancalaGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private GameRules board;
    private final Player playerOne;
    private final Player playerTwo;
    private int currentPlayerPos;
    private final ArrayList<Player> playerList;
   /* default */ Player winningPlayer;

   /**
     * Constructor to initialize the Mancala Game.
     *
     */

    public MancalaGame() {
        playerList = new ArrayList<>();
        playerOne = new Player();
        playerOne.setName("Player One");
        playerTwo = new Player();
        playerTwo.setName("Player Two");
        board = new KalahRules();
        setPlayers(playerOne, playerTwo);
        startNewGame();
    }

    /**
     * Set the players
     *
     * @param onePlayer Player one.
     * @param twoPlayer Player two.
     */
    public void setPlayers(final Player onePlayer, final Player twoPlayer) {
        playerList.clear();
        playerList.add(onePlayer);
        playerList.add(twoPlayer);
        board.registerPlayers(onePlayer, twoPlayer);
    }

    /**
     * Gets the list of players.
     *
     * @return The ArrayList of players.
     */
    public ArrayList<Player> getPlayers() {
        return playerList;
    }

    /**
     * Gets the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return playerList.get(currentPlayerPos);
    }

    /**
     * Sets the current player.
     *
     * @param Player The player.
     */
    public void setCurrentPlayer(final Player player) {
        currentPlayerPos = playerList.indexOf(player);
    }
    
    /**
     * Gets the board (ruleset) of the game.
     *
     * @return The ruleset of the game.
     */
    public GameRules getBoard() {
        return board;
    }

    /**
     * Sets the board (ruleset) of the game.
     *
     * @param Gamerules The ruleset of the game.
     */

    public void setBoard(final GameRules theBoard) {
        board = theBoard;
    }
    /**
     * Gets the number of stones in the pit.
     *
     * @param int The pit number.
     * @return The number of stones in the pit.
     * @throws PitNotFoundException If the pit is out of range.
     */

    public int getNumStones(final int pitNum) throws PitNotFoundException {
        if(pitNum>12||pitNum<1){
            throw new PitNotFoundException();
        }
        return board.getNumStones(pitNum);
    }

    // helper method to throw exception

    private void checkStartPit(final Player currentPlayer, final int startPit) throws InvalidMoveException {
        if (currentPlayer == playerOne && startPit > 6 || currentPlayer == playerTwo && startPit < 7 || startPit < 1 || startPit > 12) {
            throw new InvalidMoveException();
        }
    }

    /**
     * Completes a move on the board.
     *
     * @param int The starting pit.
     * @return The number of stones on the player's side.
     * @throws InvalidMoveException If the pit entered is out of range for the player.
     */

    public int move(final int startPit) throws InvalidMoveException {
        final Player currentPlayer = getCurrentPlayer();
        
        checkStartPit(currentPlayer, startPit);

        int totalStonesPits = 0;
        try {
            board.moveStones(startPit, playerList.indexOf(currentPlayer)+1);

            if (currentPlayer == playerOne) {
                for (int i = 1; i <= 6; i++) {
                    totalStonesPits += getNumStones(i);
                }
            } else {
                for (int i = 7; i <= 12; i++) {
                    totalStonesPits += getNumStones(i);
                }
            }
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
            setCurrentPlayer(currentPlayer);
            
        }catch (PitNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return totalStonesPits;
    }

    /**
     * Gets the player's store count.
     *
     * @param Player The player.
     * @return The player's store count.
     */

    public int getStoreCount(final Player player) throws NoSuchPlayerException{
        if (playerList.contains(player)) {
            return player.getStoreCount();
        } else {
            throw new NoSuchPlayerException();
        }
    }
    
    /**
     * Gets the winner of the game.
     *
     * @return The winning player.
     * @throws GameNotOverException If the game is not over.
     */

    public Player getWinner() throws GameNotOverException {
        winningPlayer = playerList.get(1);
        if (!isGameOver()) {
                throw new GameNotOverException();
        }
            final int storeOneStones = board.getStoreCount(1);
            final int storeTwoStones = board.getStoreCount(2);
            
            if (storeOneStones > storeTwoStones) {
                winningPlayer = playerList.get(0);
            } else if (storeTwoStones > storeOneStones) {
                winningPlayer = playerList.get(1);
            }
        return winningPlayer;
    }

    /**
     * Checks if the game is over.
     *
     */
    
    public boolean isGameOver() {
        return board.isSideEmpty(1) || board.isSideEmpty(7);
    }

    /**
     * Starts a new game by resetting the board.
     *
     */
    public void startNewGame() {
        board.resetBoard();
        currentPlayerPos = 0;
    }

    /**
     * Returns the board as a string
     *
     * @return The board as a string.
     */
    @Override
    public String toString() {
        return board.toString();
    }
}