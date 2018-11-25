package PST;

import Symbols.Symbol;
import Tokens.Token;

public class PstLeafNode extends PstNode {

    public PstLeafNode(Symbol symbol, Token token) {
        super(symbol,token);
    }

    public boolean isEpsilon() {
        return false;
    }

    @Override
    public String toString() {
        return "PstLeafNode{" +
                "symbol = " + getSymbol() +
                ", token=" + getToken() +
                '}';
    }
}
