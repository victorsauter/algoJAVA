package exceptions;

public class DisplayModeException extends Exception {
    public DisplayModeException(){
        super("The display mode given isn't correct.");
    }
}
