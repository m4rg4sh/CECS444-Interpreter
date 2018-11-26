package PST;

import Symbols.Symbol;
import Tokens.Token;

import java.util.Optional;

/**
 * This is the interface for all pst nodes. Used to create the PST and AST.
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public abstract class PstNode {
    private Symbol symbol;
    private Optional<Token> token;

    public PstNode(Symbol symbol, Token token) {
        this.symbol = symbol;
        if (null != token) {
        this.token = Optional.of(token);
        } else {
            this.token = Optional.empty();
        }

    }

    public PstNode(Symbol symbol) {
        this.symbol = symbol;
        this.token = Optional.empty();
    }

    public Symbol getSymbol() {
        return symbol;
    }

    protected void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public void setToken(Token token) {
        this.token = Optional.of(token);
    }

    public Token getToken() {
        return token.orElse(null);
    }

    public abstract boolean isEpsilon();
}
