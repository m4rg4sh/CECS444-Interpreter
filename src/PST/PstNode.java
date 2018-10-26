package PST;

import Symbols.Symbol;

public abstract class PstNode {
    private Symbol symbol;

    public PstNode(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
