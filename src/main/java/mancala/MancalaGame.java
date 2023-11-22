package mancala;

import java.util.ArrayList;
import java.io.Serializable;

public class MancalaGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private Board board;
    final private Player playerOne;
    final private Player playerTwo;
    private int currentPlayerPos;
    final private ArrayList<Player> playerList;

    public MancalaGame() {
        // initialize players and board
        final Board gameBoard = new Board();
        gameBoard.setUpPits();
        gameBoard.setUpStores();
        gameBoard.initializeBoard();
        setBoard(gameBoard);

        playerList = new ArrayList<>(); 

        playerOne = new Player("Player One");
        playerTwo = new Player("Player Two");
        setPlayers(playerOne, playerTwo);

        startNewGame();
    }

    private void setPlayers(Player onePlayer, Player twoPlayer) {
        playerList.clear();
        playerList.add(onePlayer);
        playerList.add(twoPlayer);

        board.registerPlayers(onePlayer, twoPlayer);
    }

    public ArrayList<Player> getPlayers() {
        return playerList;
    }

    public Player getCurrentPlayer() {
        return playerList.get(currentPlayerPos);
    }

    public void setCurrentPlayer(Player player) {
        currentPlayerPos = playerList.indexOf(player);
    }

    public Board getBoard() {
        return board;
    }

    private void setBoard(Board theBoard) {
        board = theBoard;
    }

    public int getNumStones(int pitNum) throws PitNotFoundException {
        return board.getNumStones(pitNum);
    }

    public int move(int startPit) throws InvalidMoveException {
        final Player currentPlayer = getCurrentPlayer();
        // throwing exceptions
        
        if(currentPlayer == playerOne && startPit>6){
            throw new InvalidMoveException();
        }
        if(currentPlayer == playerTwo && startPit<7){
            throw new InvalidMoveException();
        }

        if (startPit < 1 || startPit > 12) {
                throw new InvalidMoveException();
        }
        int totalStonesPits = 0;
        try {
            board.moveStones(startPit, currentPlayer);

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
            System.out.println("Invalid move. Please try again.");
            setCurrentPlayer(currentPlayer);
            
        }catch (PitNotFoundException e) {
            System.out.println("Pit not found!");
        }
        return totalStonesPits;
    }

    
    public int getStoreCount(Player player) throws NoSuchPlayerException{
        if (playerList.contains(player)) {
            return player.getStoreCount();
        } else {
            throw new NoSuchPlayerException();
        }
    }
    

    public Player getWinner() throws GameNotOverException {
        Player winningPlayer = null;
        try {
            // Get the winner of the game (if it's over)
            if (!isGameOver()) {
                throw new GameNotOverException();
            }

            int storeOneStones = getStoreCount(playerList.get(0));
            int storeTwoStones = getStoreCount(playerList.get(1));
            
            if (storeOneStones > storeTwoStones) {
                winningPlayer = playerList.get(0);
            } else if (storeTwoStones > storeOneStones) {
                winningPlayer = playerList.get(1);
            } 
        } catch (NoSuchPlayerException e) {
            System.out.println("Player does not exist");
        }
        return winningPlayer;
    }

    
    public boolean isGameOver() {
        try{
            return board.isSideEmpty(1) || board.isSideEmpty(7);
        }catch (PitNotFoundException e) {
                return false;
        }
    }

    private void startNewGame() {
        board.resetBoard();
        currentPlayerPos = 0;
    }

    @Override
    public String toString() {
        return board.toString();
    }
}