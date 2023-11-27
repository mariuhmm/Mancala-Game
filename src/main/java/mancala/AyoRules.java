package mancala;

public class AyoRules extends GameRules {
    private static final long serialVersionUID = 1L;
    private int currentPit=0;
    private int oppositePitIndex;
    private int stonesCaptured=0;
    private int capturedStones=0;
    private static int targetValue = 1;
    private static int playerOneFinal = 6;
    private static int playerTwoFinal = 13;

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    @Override
    public int distributeStones(final int startPit){
        final int numStones = getDataStructure().getNumStones(startPit);
        int numMoves = getDataStructure().removeStones(startPit);
    //    int lastPit = startPit + numMoves;
        currentPit = startPit;
        //capturedStones = 0;
        int playerNum;
        Countable nextElement;
       
        if(startPit<=playerOneFinal){
            playerNum=1;
        }else{
            playerNum=2;
        }
        getDataStructure().setIterator(startPit, playerNum, true);

        for(int i=0;i<numMoves;i++){
            currentPit++;
            nextElement = getDataStructure().next();
            nextElement.addStone();
            if(currentPit==playerTwoFinal){
                currentPit=1;
            }
            if(i==numMoves-1 && nextElement.getStoneCount()!=1){
                numMoves = nextElement.removeStones()+1;
                i = 0;
                
            }if(i==numMoves-1 && nextElement.getStoneCount()==0){
                capturedStones = captureStones(currentPit-1);
            }
        }
        return numStones + capturedStones;
    }

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    @Override
    public int captureStones(final int stoppingPoint){
        oppositePitIndex = 13 - stoppingPoint; // calculate the index of the opposite pit
       // stonesCaptured = 0;
        if(getDataStructure().getNumStones(stoppingPoint)==targetValue){
            stonesCaptured = getDataStructure().removeStones(oppositePitIndex);
        }
        
        return stonesCaptured;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        if (startPit < 1 || startPit > 12) {
            throw new InvalidMoveException();
        }
            if(playerNum == 1 && startPit <= 6 || playerNum == 2 && startPit > 6){
                distributeStones(startPit);
            } else {
                throw new InvalidMoveException();
            }
        return getDataStructure().getStoreCount(playerNum);
    }
    
}