package mancala;
import java.io.Serializable;

public class Store implements Countable, Serializable {

    private Player owner;
    private int stoneCount;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor to initialize the store.
     * 
     */
    public Store(){
        setOwner(owner);
        stoneCount = 0;
    }

    /**
     * Sets the owner of the store.
     *
     * @param Player The owner of the store.
     */
    public void setOwner(Player player){
        owner = player;
    }
    /**
     * Gets the owner of the store.
     *
     * @return The owner of the store. (Player)
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * Add one stone to the object.
     */
    @Override
    public void addStone(){
        stoneCount++;
    }

    /**
     * Add a specified number of stones to the object.
     *
     * @param numToAdd The number of stones to add.
     */
    @Override
    public void addStones(int amount){
        stoneCount += amount;
    }

    /**
     * Get the count of stones in the object.
     *
     * @return The count of stones.
     */

    @Override
    public int getStoneCount(){
        return stoneCount;
    }

    /**
     * Remove stones from the object.
     *
     * @return The number of stones removed.
     */
    @Override
    public int removeStones(){
        final int stonesRemoved = stoneCount;
        stoneCount = 0;
        return stonesRemoved;
    }

    /**
     * Returns the store value as a string.
     *
     * @return The store as a string.
     */
    @Override
    public String toString(){
        return "["+getStoneCount()+"]";
    }
}
