package mancala;


public class KalahRules extends GameRules {
    private static final long serialVersionUID = 1L;
    private boolean turn;
    private int currentPit=0;
    private int oppositePitIndex=0;
    private int capturedStones=0;
    private int stonesCaptured=0;
    private static int targetValue = 1;
    private static int playerOneFinal = 6;
    private static int playerTwoFinal = 13;

    /**
     * Sets an extra turn.
     * @param turnStatus The boolean value of the turn.
     */
    public void setExtraTurn(final boolean turnStatus){
        turn = turnStatus;
    }

    /**
     * Gets extra turn value.
     * @return the turn value.
     */
    public boolean extraTurn(){
        return turn;
    }

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    @Override
    public int distributeStones(int startPit){
        final int numStones = getDataStructure().getNumStones(startPit);
        final int numMoves = getDataStructure().removeStones(startPit);
        final int lastPit = startPit + numMoves;
        currentPit = startPit;
        //capturedStones = 0;
        int playerNum;
        Countable nextElement;
        if(startPit<=playerOneFinal){
            playerNum=1;
        }else{
            playerNum=2;
        }
        getDataStructure().setIterator(startPit, playerNum, false);
        
        setExtraTurn(false);

        for(int i=0;i<numMoves;i++){
            //System.err.println(i);
            currentPit++;
            nextElement = getDataStructure().next();
            nextElement.addStone();

            if(playerNum==1&&lastPit==7){
                setExtraTurn(true);
                
            }

            if(playerNum==2&&lastPit==13){
                setExtraTurn(true);
            }
            if(currentPit==playerTwoFinal){
                currentPit=1;
            }
            if(i==numMoves-1 && nextElement.getStoneCount()==1){
                capturedStones = captureStones(currentPit-1);
                getDataStructure().addToStore(playerNum, capturedStones);
                setExtraTurn(true);
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
    public int captureStones(int stoppingPoint){
        
        oppositePitIndex = 13 - stoppingPoint; // calculate the index of the opposite pit
        int numStones = 0;
        //int stonesCaptured = 0;
        if(getDataStructure().getNumStones(stoppingPoint)==targetValue){
            numStones = getDataStructure().removeStones(stoppingPoint);
            stonesCaptured = getDataStructure().removeStones(oppositePitIndex);
        }
        
        return stonesCaptured+numStones;
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
    public int moveStones(int startPit, int playerNum) throws InvalidMoveException {
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