package mancala;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private UserProfile userProfile;
    private String playerName;
    private transient Store playerStore;

    public Player(){
        playerStore = new Store();
        userProfile = new UserProfile();
    }
    
    public UserProfile getUserProfile(){
        return userProfile;
    }

    public void setUserProfile(UserProfile profile){
        userProfile = profile;
    }

    public String getName(){
        return playerName;
    }

    public void setName(String name){
        playerName = name;
        userProfile.setPlayerName(name);
    }

    public Store getStore(){
        return playerStore;
    }

    public int getStoreCount(){
        return playerStore.getStoneCount();
    }

    public void setStore(Store store){
        playerStore = store;
    }

    @Override
    public String toString(){
        return playerName + playerStore.toString();
    }

}
