package mancala;
import java.io.Serializable;

public class Pit implements Countable, Serializable {
    private int numStones;

    public Pit(){
        numStones = 0;
    }

    @Override
    public int getStoneCount(){
        return numStones;
    }

    @Override
    public void addStone(){
        numStones++;
    }

    @Override
    public void addStones(int amount){
        numStones+=amount;
    }

    @Override
    public int removeStones(){
        final int removedStones = numStones;
        numStones = 0;
        return removedStones;
    }

    @Override
    public String toString(){
        return "["+numStones+"]";
    }
}
