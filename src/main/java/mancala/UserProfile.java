package mancala;

import java.io.Serializable;

public class UserProfile implements Serializable{
    private static final long serialVersionUID = 1L;
    private String playerName;
    private int kalahGames;
    private int ayoGames;
    private int kalahWins;
    private int ayoWins;

    public UserProfile(){
        playerName = "hi";
        kalahGames = 0;
        ayoGames = 0;
        kalahWins = 0;
        ayoWins = 0;
    }

    public void kalahGamesPlayed(){
        kalahGames++;
    }

    public void ayoGamesPlayed(){
        ayoGames++;
    }

    public String getPlayerName(){
        return playerName;
    }
}