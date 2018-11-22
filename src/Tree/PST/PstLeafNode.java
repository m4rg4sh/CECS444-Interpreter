package Tree.PST;

import Symbols.Symbol;
import Tokens.Token;

public class PstLeafNode extends PstNode {
    private Token token;
    //TODO according to guide: Leaf node should know the token it was built for; for 1) variable name and 2) error line # info
    //I don't really get all of this tbh

    public PstLeafNode(Symbol symbol, Token token) {
        super(symbol);
        this.token = token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
    
    @Override
    public String toString() {
        return "PstLeafNode{" +
                "symbol = " + getSymbol() +
                ", token=" + token +
                '}';
    }
}
