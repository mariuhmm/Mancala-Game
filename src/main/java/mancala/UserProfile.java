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

    public void kalahGames(){
        kalahGames++;
    }

    public int getKalahGames(){
        return kalahGames;
    }

    public void ayoGames(){
        ayoGames++;
    }

    public int getAyoGames(){
        return ayoGames;
    }

    public void ayoWins(){
        ayoWins++;
    }

    public int getAyoWins(){
        return ayoWins;
    }

    public void kalahWins(){
        kalahWins++;
    }

    public int getKalahWins(){
        return kalahWins;
    }

    public void setPlayerName(String name){
        playerName = name;
    }

    public String getPlayerName(){
        return playerName;
    }
}