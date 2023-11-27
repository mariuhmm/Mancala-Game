package mancala;

import java.io.Serializable;

public class UserProfile implements Serializable{
    private static final long serialVersionUID = 1L;
    private String playerName;
    private int kalahPlayed;
    private int ayoPlayed;
    private int kalahWon;
    private int ayoWon;
    private int ayoLost;
    private int kalahLost;

    public UserProfile(){
        playerName = "null";
        kalahPlayed = 0;
        ayoPlayed = 0;
        kalahWon = 0;
        ayoWon = 0;
        kalahLost = 0;
        ayoLost = 0;
    }

    /**
     * Increment's the number of Kalah games played.
     * 
     */

    public void kalahGames(){
        kalahPlayed++;
    }
     /**
     * Gets the number of Kalah games played
     * 
     * @return The number of Kalah games played
     */
    public int getKalahGames(){
        return kalahPlayed;
    }

     /**
     * Increment's the number of Ayo games played.
     * 
     */
    public void ayoGames(){
        ayoPlayed++;
    }

     /**
     * Gets the number of Ayo games played
     * 
     * @return The number of Ayo games played
     */
    public int getAyoGames(){
        return ayoPlayed;
    }
     /**
     * Increment's the number of Ayo wins.
     * 
     */
    public void ayoWins(){
        ayoWon++;
    }

     /**
     * Gets the number of Ayo games won.
     * 
     * @return The number of Ayo games won.
     */
    public int getAyoWins(){
        return ayoWon;
    }

     /**
     * Increment's the number of Kalah wins.
     * 
     */
    public void kalahWins(){
        kalahWon++;
    }

     /**
     * Gets the number of Kalah games won
     * 
     * @return The number of Kalah games won
     */
    public int getKalahWins(){
        return kalahWon;
    }

    /**
     * Increments the number of Kalah games lost.
     * 
     */
     
    public void kalahLoss(){
        kalahLost++;
    }

    /**
     * Increments the number of Ayo games lost.
     * 
     */
    public void ayoLoss(){
        ayoLost++;
    }

    /**
     * Gets the number of Kalah games lost
     * 
     * @return The number of Kalah games lost
     */
    public int getKalahLosses(){
        return kalahLost;
    }

    /**
     * Gets the number of Ayo games lost
     * 
     * @return The number of Ayo games lost
     */
    public int getAyoLosses(){
        return ayoLost;
    }

    /**
     * Sets the name of the profile
     *
     * @param name The name of the profile.
     */
    public void setPlayerName(final String name){
        playerName = name;
    }

    /**
     * Gets the name of the profile.
     *
     * @return The name of the profile.
     */
    public String getPlayerName(){
        return playerName;
    }
}