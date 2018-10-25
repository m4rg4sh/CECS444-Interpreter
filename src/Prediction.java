import Symbols.NonTerminal;
import Symbols.Terminal;

/**
 *  This class represents the Tupel (Non-Terminal, terminal) that is used to check the prediction table for our LL parse machine.
 */
public class Prediction {
    private NonTerminal LHS;
    private Terminal terminal;
    
    public Prediction(NonTerminal lhs, Terminal terminal){
        this.LHS = lhs;
        this.terminal = terminal;
    }
    
    @Override
    public int hashCode() {
        return (LHS.toString() + terminal).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Prediction) {
            Prediction otherObject = (Prediction) obj;
            return otherObject.LHS.equals(LHS) && otherObject.terminal == terminal;
        } else {
            return false;
        }
    }
}
