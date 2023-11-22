package mancala;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private UserProfile userProfile;
    private String playerName;
    private Store playerStore;

    public Player(){
        playerStore = new Store();
    }
    
    public UserProfile getProfile(){
        return userProfile;
    }

    public Player(String name){
        playerName = name;
        playerStore = new Store();
    }

    public String getName(){
        return userProfile.getPlayerName();
    }

    private void setName(String name){
        playerName = name;
    }

    public Store getStore(){
        return playerStore;
    }

    public int getStoreCount(){
        return playerStore.getTotalStones();
    }

    public void setStore(Store store){
        playerStore = store;
    }

    @Override
    public String toString(){
        return playerName + playerStore.toString();
    }

}
