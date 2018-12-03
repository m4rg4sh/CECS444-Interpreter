package Parser;

import Symbols.NonTerminal;
import Symbols.Terminal;

/**
 *  This class represents the Tuple (Non-Terminal, terminal) that is used to check the prediction table for our LL parse machine.
 *
 *  @author Kevin Bui <Kevinthuybui@gmail.com>
 */
public class Prediction {
    private NonTerminal nonTerminal;
    private Terminal terminal;

    public Prediction(NonTerminal nonTerminal, Terminal terminal){
        this.nonTerminal = nonTerminal;
        this.terminal = terminal;
    }

    /**
     * @return the hashcode of the prediciton
     */
    @Override
    public int hashCode() {
        return (nonTerminal.toString() + terminal).hashCode();
    }

    /**
     * Compares the prediction to a arbitrary object
     * @param obj the object to be compared to
     * @return true if they are the same tuple of NonTerminal and Terminal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Prediction) {
            Prediction otherObject = (Prediction) obj;
            return otherObject.nonTerminal.equals(nonTerminal) && otherObject.terminal == terminal;
        } else {
            return false;
        }
    }
}
