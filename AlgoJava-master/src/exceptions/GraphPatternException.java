package exceptions;

public class GraphPatternException extends Exception {
    public GraphPatternException(){
        super("The string pattern symbolizing the graph is wrong.");
    }
}
