package mancala;

import java.io.Serializable;

public class UserProfile implements Serializable{
    private static final long serivalVersionUID = 1L;
    private String playerName;
    private int kalahGames;
    private int ayoGames;
    private int kalahWins;
    private int ayoWins;

    public void kalahGamesPlayed(){
        kalahGames++;
    }

    public void ayoGamesplayed(){
        ayoGames++;
    }

    public String getPlayerName(){
        return playerName;
    }
}