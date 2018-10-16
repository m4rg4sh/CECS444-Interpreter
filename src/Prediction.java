/**
 *  This class represents the tuple (LHS, terminal) that is used to check the prediction table for our LL parse machine.
 */
public class Prediction {
    private NonTerminalSymbol LHS;
    private char terminal;
    
    public Prediction(NonTerminalSymbol LHS, char terminal){
        this.LHS = LHS;
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
