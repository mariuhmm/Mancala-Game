package mancala;

import java.util.ArrayList;
import java.io.Serializable;

public class MancalaGame implements Serializable {
    private GameRules board;
    private final Player playerOne;
    private final Player playerTwo;
    private int currentPlayerPos;
    private final ArrayList<Player> playerList;

    public MancalaGame() {
        playerList = new ArrayList<>();
        playerOne = new Player();
        playerOne.setName("Player One");
        playerTwo = new Player();
        playerTwo.setName("Player Two");
        board = new KalahRules();
        setBoard(board);    // default of kalahrules
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

    public void setBoard(GameRules theBoard) {
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
        Player winningPlayer = playerList.get(1);
        if (!isGameOver()) {
                throw new GameNotOverException();
        }
            int storeOneStones = board.getStoreCount(1);
            int storeTwoStones = board.getStoreCount(2);
            
            if (storeOneStones > storeTwoStones) {
                winningPlayer = playerList.get(0);
            } else if (storeTwoStones > storeOneStones) {
                winningPlayer = playerList.get(1);
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