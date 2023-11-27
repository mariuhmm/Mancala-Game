package mancala;
import java.io.Serializable;

public class Pit implements Countable, Serializable {
    private static final long serialVersionUID = 1L;
    private int numStones;

    /**
     * Pit constructor.
     */
    public Pit(){
        numStones = 0;
    }
    /**
     * Get the count of stones in the object.
     *
     * @return The count of stones.
     */
    @Override
    public int getStoneCount(){
        return numStones;
    }

    /**
     * Add one stone to the object.
     */
    @Override
    public void addStone(){
        numStones++;
    }

    /**
     * Add a specified number of stones to the object.
     *
     * @param numToAdd The number of stones to add.
     */
    @Override
    public void addStones(int amount){
        numStones+=amount;
    }

    /**
     * Remove stones from the object.
     *
     * @return The number of stones removed.
     */
    @Override
    public int removeStones(){
        final int removedStones = numStones;
        numStones = 0;
        return removedStones;
    }

    /**
     * Gets string value of pits.
     * @return String of pit value
     */
    @Override
    public String toString(){
        return "["+numStones+"]";
    }
}
