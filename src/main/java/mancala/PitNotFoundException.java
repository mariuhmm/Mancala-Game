package mancala;

public class PitNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public PitNotFoundException(){
        super("Error: Pit not found.");
    }
}