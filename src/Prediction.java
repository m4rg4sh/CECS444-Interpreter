import Symbols.NonTerminal;
import Symbols.Terminal;

/**
 *  This class represents the Tupel (Non-Terminal, terminal) that is used to check the prediction table for our LL parse machine.
 */
public class Prediction {
    private NonTerminal lhs;
    private Terminal terminal;

    public Prediction(NonTerminal lhs, Terminal terminal){
        this.lhs = lhs;
        this.terminal = terminal;
    }
    
    @Override
    public int hashCode() {
        return (lhs.toString() + terminal).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Prediction) {
            Prediction otherObject = (Prediction) obj;
            return otherObject.lhs.equals(lhs) && otherObject.terminal == terminal;
        } else {
            return false;
        }
    }
}
