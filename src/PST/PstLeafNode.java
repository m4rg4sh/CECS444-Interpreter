package PST;

import Symbols.Symbol;
import Tokens.Token;

public class PstLeafNode extends PstNode {
    private Token token;

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
}
