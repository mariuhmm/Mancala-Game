package mancala;

import java.util.ArrayList;
import java.io.Serializable;

public class MancalaGame implements Serializable {
    private GameRules board;
    private final Player playerOne;
    private final Player playerTwo;
    private int currentPlayerPos;
    private final ArrayList<Player> playerList;

    public MancalaGame(GameRules board) {
        playerList = new ArrayList<>();
        playerOne = new Player("Player One");
        playerTwo = new Player("Player Two");
        this.board = board;
        setBoard(board);
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

    public GameRules getBoard() {
        return board;
    }

    private void setBoard(GameRules theBoard) {
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
            System.out.println(e.getMessage());
        }
        return winningPlayer;
    }

    
    public boolean isGameOver() {
        return board.isSideEmpty(1) || board.isSideEmpty(7);
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