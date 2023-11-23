package mancala;

public class AyoRules extends GameRules {

    @Override
    public int distributeStones(int startingPoint){
        int numStones = getDataStructure().getNumStones(startingPoint);
        int numMoves = getDataStructure().removeStones(startingPoint);
        int currentPit = startingPoint;
        int capturedStones = 0;
        int lastPit = startingPoint + numMoves;
        while (numMoves > 0) {
            currentPit++;

            if(currentPit == 13){
                currentPit = 1;
            }

            if(currentPit == startingPoint){
                continue;
            }

            if(numMoves==0&&getDataStructure().getNumStones(currentPit)==1){
                capturedStones = captureStones(currentPit);
                if(startingPoint >= 1 && startingPoint <= 6&&capturedStones!=0){
                    getDataStructure().addToStore(0,capturedStones);
                }
                if(startingPoint>6 &&capturedStones!=0){
                    getDataStructure().addToStore(1,capturedStones);
                }
            }
            else{
                numMoves = getDataStructure().getNumStones(currentPit);
            }
        }
        return numStones + capturedStones;
    }

    @Override
    public int captureStones(int stoppingPoint){
        int oppositePitIndex = 13 - stoppingPoint; // calculate the index of the opposite pit
        int stonesCaptured = getDataStructure().removeStones(oppositePitIndex);

        return stonesCaptured;
    }

    @Override
    public int moveStones(int startPit, int playerNum) throws InvalidMoveException {
      /*  if (startPit < 1 || startPit > 12) {
            throw new InvalidMoveException();
        }*/
        int totalStonesAdded = 0;
            if(playerNum == 1 && startPit <= 6 || playerNum == 2 && startPit > 6){
                totalStonesAdded = distributeStones(startPit);
            } /*else {
                throw new InvalidMoveException();
            }*/
        return getDataStructure().getStoreCount(playerNum);
    }
    
}