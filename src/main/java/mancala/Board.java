package mancala;

import java.util.ArrayList;

public class Board {
    private ArrayList<Pit> pitList;
    private ArrayList<Store> storeList;
    private Player playerOne;
    private Player playerTwo;
    private boolean turn;

    public Board() {
        pitList = new ArrayList<>();
        storeList = new ArrayList<>();
        initializeBoard();
        setUpStores();
        setUpPits();
    }

    public void initializeBoard() {
        // Initialize pits
        for (Pit pit : pitList) {
            for(int i=0;i<4;i++){
                pit.addStone();
            }
        }
        
    }

    public void setUpPits() {
        for (int i = 0; i < 12; i++) {
            pitList.add(new Pit());
        }
    }

    public ArrayList<Pit> getPits() {
        return pitList;
    }

    public void resetBoard() {
        // Reset pits
        for (Pit pit : pitList) {
            pit.removeStones();
            for(int i=0;i<4;i++){
                pit.addStone();
            }
        }
        for (Store store : storeList){
            store.emptyStore();
        }
    }

    public void setUpStores() {
        storeList.add(new Store());
        storeList.add(new Store());
    }


    public ArrayList<Store> getStores() {
        return storeList;
    }

    public void registerPlayers(Player one, Player two) {
        playerOne = one;
        playerTwo = two;
        playerOne.setStore(storeList.get(0));
        playerTwo.setStore(storeList.get(1));
    }

    public boolean isSideEmpty(int pitNum) throws PitNotFoundException {
        if (pitNum < 1 || pitNum > 12) {
                throw new PitNotFoundException();
        }
        if (pitNum >= 1 && pitNum <= 6) {
            for (int i = 0; i <= 5; i++) {
                if (pitList.get(i).getStoneCount() > 0) {
                    return false;
                }
            }
            return true;
        }
        if (pitNum >= 7) {
            for (int i = 6; i <= 11; i++) {
                if (pitList.get(i).getStoneCount() > 0) {
                    return false;
                }
            }
            return true;
        }
    
        return false; 
    }  

    public int getNumStones(int pitNum) throws PitNotFoundException {
        if (pitNum < 1 || pitNum > 12) {
            throw new PitNotFoundException();
        }
        return pitList.get(pitNum - 1).getStoneCount();
    }
    
    // helper methods for extra turns
    private void setExtraTurn(boolean turnStatus){
        turn = turnStatus;
    }

    public boolean extraTurn(){
        return turn;
    }

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

    public int captureStones(int stoppingPoint) throws PitNotFoundException {
            if (stoppingPoint < 1 || stoppingPoint > 12) {
                throw new PitNotFoundException();
            }
            int oppositePitIndex = 13 - stoppingPoint; // calculate the index of the opposite pit

            Pit oppositePit = pitList.get(oppositePitIndex-1);

            int stonesCaptured = oppositePit.removeStones();

        return stonesCaptured;
    }

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

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append("\t");
        for (int i = 12; i >= 7; i--) {
            boardString.append(pitList.get(i - 1).getStoneCount()).append("\t");
        }
        boardString.append("\n" + storeList.get(1).getTotalStones()).append("\t\t\t\t\t\t\t");
        boardString.append(storeList.get(0).getTotalStones()).append("\n\t");
        
        for (int i = 1; i <= 6; i++) {
            boardString.append(pitList.get(i - 1).getStoneCount()).append("\t");
        }
        return boardString.toString();
    }
}
