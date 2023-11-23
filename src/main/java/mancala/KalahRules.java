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
    public int distributeStones(int startingPoint){
        int numStones = getDataStructure().getNumStones(startingPoint);
        int numMoves = getDataStructure().removeStones(startingPoint);
        int currentPit = startingPoint;
        int capturedStones = 0;
        int lastPit = startingPoint + numMoves;
        while (numMoves > 0) {
            currentPit++;
            if (currentPit == 7 && startingPoint >= 1 && startingPoint <= 6) {
                if(lastPit==7){
                    setExtraTurn(true);
                    numMoves--;
                }
                getDataStructure().addToStore(1,1);
                numMoves--;
            } else if (currentPit == 13 && startingPoint >= 7 && startingPoint <= 12) {
                if(lastPit==13){
                    setExtraTurn(true);
                    numMoves--;
                }
                getDataStructure().addToStore(2,1);
                numMoves--;
                currentPit = 1;
            }
            if(numMoves!=-1){
                getDataStructure().addStones(currentPit,1);
            }
            numMoves--;
            if(numMoves==0&&getDataStructure().getNumStones(currentPit)==1){
                capturedStones = captureStones(currentPit);
                if(startingPoint >= 1 && startingPoint <= 6&&capturedStones!=0){
                    getDataStructure().addToStore(1,capturedStones);
                    setExtraTurn(true);
                }
                if(startingPoint>6 &&capturedStones!=0){
                    getDataStructure().addToStore(2,capturedStones);
                    setExtraTurn(true);
                }
            }
            if(numMoves==1){
                setExtraTurn(false);
            }
        }return numStones + capturedStones;
    }

    @Override
    public int captureStones(int stoppingPoint){
        int oppositePitIndex = 13 - stoppingPoint; // calculate the index of the opposite pit
        int stonesCaptured = getDataStructure().removeStones(oppositePitIndex);

        return stonesCaptured;
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