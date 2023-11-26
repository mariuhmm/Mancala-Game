package mancala;

public class AyoRules extends GameRules {

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
        getDataStructure().setIterator(startPit, playerNum, true);

        for(int i=0;i<numMoves;i++){
            currentPit++;
            nextElement = getDataStructure().next();
            nextElement.addStone();
            if(currentPit==13){
                currentPit=1;
            }
            if(i==numMoves-1 && nextElement.getStoneCount()!=1){
                numMoves = nextElement.removeStones()+1;
                i = 0;
                
            }if(i==numMoves-1 && nextElement.getStoneCount()==0){
                captureStones(currentPit-1);
            }
        }
        return numStones + capturedStones;
    }

    @Override
    public int captureStones(int stoppingPoint){
        int oppositePitIndex = 13 - stoppingPoint; // calculate the index of the opposite pit
        int stonesCaptured = 0;
        if(getDataStructure().getNumStones(stoppingPoint)==1){
            stonesCaptured = getDataStructure().removeStones(oppositePitIndex);
        }
        
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