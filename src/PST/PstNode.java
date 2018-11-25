package PST;

import Symbols.Symbol;

public abstract class PstNode {
    private Symbol symbol;

    //TODO store the rule that was used

    public PstNode(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    protected void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public abstract boolean isEpsilon();
}
