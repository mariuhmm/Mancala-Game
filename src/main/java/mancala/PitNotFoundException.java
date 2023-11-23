package mancala;

public class PitNotFoundException extends Exception {
    public PitNotFoundException(){
        super("Error: Pit not found.");
    }
}