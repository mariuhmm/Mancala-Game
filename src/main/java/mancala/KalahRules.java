package mancala;

import java.util.ArrayList;

public class KalahRules extends GameRules {
    private boolean turn;

    // helper methods for extra turns
    public void setExtraTurn(boolean turnStatus){
        turn = turnStatus;
    }

    public boolean extraTurn(){
        return turn;
    }

    @Override
    public int distributeStones(int startPit){
        int numStones = getDataStructure().getNumStones(startPit);
        int numMoves = getDataStructure().removeStones(startPit);
        int lastPit = startPit + numMoves;
        int currentPit = startPit;
        int capturedStones = 0;
        int playerNum;
        Countable nextElement;
        if(startPit<=6){
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
            if(currentPit==13){
                currentPit=1;
            }
            if(i==numMoves-1 && getDataStructure().getNumStones(currentPit-1)==1){
                capturedStones = captureStones(currentPit-1);
                getDataStructure().addToStore(playerNum, capturedStones);
                setExtraTurn(true);
            }
        
        }
        return numStones + capturedStones;
    }
 
    @Override
    public int captureStones(int stoppingPoint){
        int oppositePitIndex = 13 - stoppingPoint; // calculate the index of the opposite pit
        int numStones = getDataStructure().removeStones(stoppingPoint);
        int stonesCaptured = getDataStructure().removeStones(oppositePitIndex);

        return stonesCaptured+numStones;
    }

    @Override
    public int moveStones(int startPit, int playerNum) throws InvalidMoveException {
        if (startPit < 1 || startPit > 12) {
            throw new InvalidMoveException();
        }
        int totalStonesAdded = 0;
            if(playerNum == 1 && startPit <= 6 || playerNum == 2 && startPit > 6){
                totalStonesAdded = distributeStones(startPit);
            } else {
                throw new InvalidMoveException();
            }
        return getDataStructure().getStoreCount(playerNum);
    }
    
}