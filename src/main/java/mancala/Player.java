package mancala;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private UserProfile userProfile;
    private String playerName;
    private transient Store playerStore;

    /**
     * Player constructor
     */

    public Player(){
        playerStore = new Store();
        userProfile = new UserProfile();
    }

    /**
     * Gets the user profile
     *
     * @return The user profile.
     */
    public UserProfile getUserProfile(){
        return userProfile;
    }

    /**
     * Sets the user profile.
     *
     * @param UserProfile the player's user profile
     */
    public void setUserProfile(UserProfile profile){
        userProfile = profile;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName(){
        return playerName;
    }

    /**
     * Sets the name of the player
     *
     * @param name The name of the player.
     */
    public void setName(String name){
        playerName = name;
        userProfile.setPlayerName(name);
    }

    /**
     * Gets the player's store
     *
     * @return The player's store
     */
    public Store getStore(){
        return playerStore;
    }

    /**
     * Gets the player's store count
     *
     * @return The player's store count.
     */
    public int getStoreCount(){
        return playerStore.getStoneCount();
    }
    /**
     * Sets the player's store
     *
     * @param Store The player's store.
     */
    public void setStore(Store store){
        playerStore = store;
    }

    /**
     * Returns the player's name and store as a string
     *
     * @return The player's name and store as a string
     */
    @Override
    public String toString(){
        return playerName + playerStore.toString();
    }

}
