package mancala;
public class Store implements Countable {

    private Player owner;
    private int stoneCount;

    public Store(){
        owner = null;
        stoneCount = 0;
    }

    private void setOwner(Player player){
        owner = player;
    }

    public Player getOwner(){
        return owner;
    }

    @Override
    public void addStone(){
        stoneCount++;
    }

    @Override
    public void addStones(int amount){
        stoneCount += amount;
    }

    public int getStoneCount(){
        return stoneCount;
    }

    @Override
    public int removeStones(){
        final int stonesRemoved = stoneCount;
        stoneCount = 0;
        return stonesRemoved;
    }

    @Override
    public String toString(){
        return "["+getStoneCount()+"]";
    }
}
