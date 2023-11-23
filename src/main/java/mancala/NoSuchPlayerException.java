package mancala;

public class NoSuchPlayerException extends Exception {
    public NoSuchPlayerException(){
        super("Error: No such player.");
    }
    
}