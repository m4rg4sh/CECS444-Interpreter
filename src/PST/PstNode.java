package PST;

import Symbols.Symbol;
import Tokens.Token;

import java.util.Optional;

public abstract class PstNode {
    private Symbol symbol;
    private Optional<Token> token;

    public PstNode(Symbol symbol, Token token) {
        this.symbol = symbol;
        this.token = Optional.of(token);
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

    protected void setToken(Token token) {
        this.token = Optional.of(token);
    }

    public Token getToken() {
        return token.orElse(null);
    }

    public abstract boolean isEpsilon();
}
