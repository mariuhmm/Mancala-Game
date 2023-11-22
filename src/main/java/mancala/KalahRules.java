package mancala;

import java.util.ArrayList;

public class KalahRules extends GameRules {
    private ArrayList<Pit> pitList;
    private ArrayList<Store> storeList;
    private Player playerOne;
    private Player playerTwo;
    private boolean turn;

    // helper methods for extra turns
    private void setExtraTurn(boolean turnStatus){
        turn = turnStatus;
    }

    public boolean extraTurn(){
        return turn;
    }

    @Override
    public int distributeStones(int startingPoint) throws PitNotFoundException {
        if (startingPoint < 1 || startingPoint > 12) {
            throw new PitNotFoundException();
        }
        Pit startingPit = pitList.get(startingPoint - 1);
        int numStones = startingPit.getStoneCount();
        int numMoves = startingPit.removeStones();
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
                storeList.get(0).addStones(1);
                numMoves--;
            } else if (currentPit == 13 && startingPoint >= 7 && startingPoint <= 12) {
                if(lastPit==13){
                    setExtraTurn(true);
                    numMoves--;
                }
                storeList.get(1).addStones(1);
                numMoves--;
                currentPit = 1;
            }
            if(numMoves!=-1){
                pitList.get(currentPit - 1).addStone();
            }
            numMoves--;
            if(numMoves==0&&pitList.get(currentPit-1).getStoneCount()==1){
                capturedStones = captureStones(currentPit);
                if(startingPoint >= 1 && startingPoint <= 6&&capturedStones!=0){
                    storeList.get(0).addStones(capturedStones);
                    setExtraTurn(true);
                }
                if(startingPoint>6 &&capturedStones!=0){
                    storeList.get(1).addStones(capturedStones);
                    setExtraTurn(true);
                }
            }
            if(numMoves==1){
                setExtraTurn(false);
            }
        }return numStones + capturedStones;
    }

    @Override
    public int captureStones(int stoppingPoint) throws PitNotFoundException {
            if (stoppingPoint < 1 || stoppingPoint > 12) {
                throw new PitNotFoundException();
            }
            int oppositePitIndex = 13 - stoppingPoint; // calculate the index of the opposite pit

            Pit oppositePit = pitList.get(oppositePitIndex-1);

            int stonesCaptured = oppositePit.removeStones();

        return stonesCaptured;
    }

    @Override
    public int moveStones(int startPit, Player player) throws InvalidMoveException {
    if (startPit < 1 || startPit > 12) {
        throw new InvalidMoveException();
    }

    int totalStonesAdded = 0;  // initialize totalStonesAdded

    try {
        if (player == playerOne && startPit <= 6 || player == playerTwo && startPit > 6) {
            totalStonesAdded = distributeStones(startPit);
        } else {
            throw new InvalidMoveException();
        }
    } catch (PitNotFoundException e) {
        System.out.println("Pit not found!");
    }

    return player.getStoreCount();
    }
}