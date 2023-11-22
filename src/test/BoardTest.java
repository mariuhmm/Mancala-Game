package mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTest {
  private Board board;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        board = new Board();
        board.initializeBoard();
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        board.registerPlayers(player1, player2);

    }

    private int getPitValue(int pit){
        return board.getPits().get(pit-1).getStoneCount();
    }

    private int getStoreValue(int store){
        return board.getStores().get(store-1).getTotalStones();
    }

    @Test
    public void testCaptureStones() throws PitNotFoundException{
        
        int stonesCapturedbyP1 = board.captureStones(3);

        int stonesCapturedbyP2 = board.captureStones(9);
        
        assertEquals(4,stonesCapturedbyP1);
        assertEquals(0,getPitValue(10));

        assertEquals(4,stonesCapturedbyP2);
        assertEquals(0,getPitValue(4));

        assertThrows(PitNotFoundException.class, () -> board.captureStones(0));
        assertThrows(PitNotFoundException.class, () -> board.captureStones(13));
    }

    @Test
    public void testDistributeStones() throws PitNotFoundException{
        board.getPits().get(3).addStone();
        board.getPits().get(9).addStone();

        int stonesDistributedbyP1 = board.distributeStones(4);

        int stonesDistributedbyP2 = board.distributeStones(10);
        
        assertEquals(5,stonesDistributedbyP1);
        assertEquals(0,getPitValue(4));
        assertEquals(1,player1.getStoreCount());
        assertEquals(5,getPitValue(5));
        assertEquals(5,getPitValue(6));
        assertEquals(5,getPitValue(7));
        assertEquals(5,getPitValue(8));

        assertEquals(5,stonesDistributedbyP2);
        assertEquals(0,getPitValue(10));
        assertEquals(1,player2.getStoreCount());
        assertEquals(5,getPitValue(11));
        assertEquals(5,getPitValue(12));
        assertEquals(5,getPitValue(1));
        assertEquals(5,getPitValue(2));

        assertThrows(PitNotFoundException.class, () -> board.distributeStones(0));
        assertThrows(PitNotFoundException.class, () -> board.distributeStones(13));
    }
    
    @Test
    public void testNumStones() throws PitNotFoundException{

        int pitCount = board.getNumStones(3);
        
        assertEquals(4,pitCount);
        
        assertThrows(PitNotFoundException.class, () -> board.getNumStones(0));
        assertThrows(PitNotFoundException.class, () -> board.getNumStones(13));
    }

    @Test
    public void testSideEmpty() throws PitNotFoundException{

        boolean isEmpty = board.isSideEmpty(3);
        assertEquals(false,isEmpty);
        // empty player 1 side
        for(int i=0;i<6;i++){
            board.getPits().get(i).removeStones();
        }
        isEmpty = board.isSideEmpty(3);
        assertEquals(true,isEmpty);
        isEmpty = board.isSideEmpty(8);
        assertEquals(false,isEmpty);
        
        board.resetBoard();

        // empty player 2 side
        for(int i=6;i<12;i++){
            board.getPits().get(i).removeStones();
        }

        isEmpty = board.isSideEmpty(8);
        assertEquals(true,isEmpty);
        isEmpty = board.isSideEmpty(3);
        assertEquals(false,isEmpty);
        
        assertThrows(PitNotFoundException.class, () -> board.getNumStones(0));
        assertThrows(PitNotFoundException.class, () -> board.getNumStones(13));
        
    }

    @Test
    public void testResetBoard(){

        // add stone to each pit
        for(int i=0;i<12;i++){
            board.getPits().get(i).addStone();
            assertEquals(5,getPitValue(i+1));
        }

        // add stone to each players store
        board.getStores().get(0).addStones(1);
        board.getStores().get(1).addStones(1);
        
        assertEquals(1,player1.getStoreCount());
        assertEquals(1,player2.getStoreCount());

        board.resetBoard();

        // check if values were reset
        for(int i=0;i<12;i++){
            assertEquals(4,getPitValue(i+1));
        }

        assertEquals(0,player1.getStoreCount());
        assertEquals(0,player2.getStoreCount());
    }

    @Test
    public void testRegisterPlayers() {
        // create player instances
        Player player1Test = new Player("Player One");
        Player player2Test = new Player("Player Two");

        board.registerPlayers(player1Test, player2Test);

        // check if stores connect

        assertEquals(player1Test.getStore(), board.getStores().get(0));
        assertEquals(player2Test.getStore(), board.getStores().get(1));
    }

    @Test
    public void testSetUpPitsAndGetPits() {

        for (Pit pit : board.getPits()) {
            assertEquals(4, pit.getStoneCount());
        }
    }



        @Test
    public void testMoveStonesValidMove() throws InvalidMoveException,PitNotFoundException {
        // Assuming you have a valid move (e.g., startPit = 1)
        int startPit = 3;

        // Perform the move
        int stonesAddedToStore = board.moveStones(startPit, player1);
        assertEquals(5,getPitValue(5));

        assertEquals(1,player1.getStoreCount());

    }

    @Test
    public void testMoveStonesInvalidMove() {
        // Assuming you have an invalid move (e.g., startPit = 0, which is out of bounds)

        // The method should throw an InvalidMoveException for an invalid move
        assertThrows(InvalidMoveException.class, () -> board.moveStones(14, player1));
    }

    @Test
    public void testMoveStonesCaptureStones() throws InvalidMoveException, PitNotFoundException {
    // Set up the initial board state to create the capturing condition
    assertEquals(4,getPitValue(2));
    board.getPits().get(5).removeStones(); // Make a specific pit empty on Player1's side
    // Perform the move that captures opponent's stones
    System.err.println("Captured pit value: "+ getPitValue(7));
    int startPit = 2; // Assuming this move captures stones
    int stonesAddedToStore = board.moveStones(startPit, player1);
    System.err.println("Captured pit value: "+ getPitValue(7));
    // Assertions
    assertEquals(0, getPitValue(7)); // check if captured pit is empty
    assertEquals(4, player1.getStoreCount()); // Check that Player1's store has increased by the expected amount
    assertEquals(4, stonesAddedToStore); // Check the number of stones captured
    }


}
