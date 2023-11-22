package mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class MancalaGameTest {
    private MancalaGame game;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        game = new MancalaGame();
        Board board = new Board();
        board.setUpPits();
        board.setUpStores();
        board.initializeBoard();
        game.setBoard(board);
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        game.setPlayers(player1, player2);
        game.startNewGame();
    }
  private void emptyBoard(){
    for(Pit p: game.getBoard().getPits()){
        p.removeStones();
    }

  }

  @Test
    public void testGetPlayers() {

        boolean players = true;
        List<Player> actualPlayers = game.getPlayers();
        for(Player p:actualPlayers){
            if(p!=player1){
                if(p!=player2){
                    players = false;
                    break;
                }
            }
        }
        assertTrue(players);
    }

   @Test
    public void testGetNumStonesValidPit() throws PitNotFoundException {
        // Assuming you have a specific pit with a known number of stones (e.g., pit 1 has 5 stones)
        int pitNum = 1;
        int expectedStones = 4;

        assertEquals(expectedStones, game.getNumStones(pitNum));
    }

    @Test
    public void testGetNumStonesInvalidPit() {
        // Assuming you're testing an invalid pit number (e.g., pit 0, which is out of bounds)
        int invalidPitNum = 14;

        // The method should throw a PitNotFoundException for an invalid pit number
        assertThrows(PitNotFoundException.class, () -> game.getNumStones(invalidPitNum));
    }


    @Test
    public void testGetStoreCountValidPlayer() throws NoSuchPlayerException {
        // Assuming you have set up player stores with known stone counts
        player1.getStore().addStones(10);


        // Verify the store count for player1
        int expectedStoreCountPlayer1 = 10;
        int actualStoreCountPlayer1 = game.getStoreCount(player1);
        assertEquals(expectedStoreCountPlayer1, actualStoreCountPlayer1);

    }

  


    @Test
    public void testGetWinnerGameNotOver() {
        // Test that the method throws GameNotOverException when the game is not over
        assertThrows(GameNotOverException.class, () -> game.getWinner());
    }
  
}
