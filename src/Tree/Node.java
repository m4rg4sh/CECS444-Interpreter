package Tree;

import Symbols.Symbol;
import Symtab.SymtabEntry;
import Tokens.Token;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * This is the abstract Class for all tree nodes. Used to create the PST and AST.
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public abstract class Node {
    private Symbol symbol;
    private Optional<Token> token;
    private SctNode scope;
    private int symtabIndex;

    /**
     * Constructor
     * @param symbol the symbol of the node
     * @param token the token that created the node
     */
    public Node(Symbol symbol, Token token) {
        this.symbol = symbol;
        this.scope = null;
        symtabIndex = -1;
        if (null != token) {
        this.token = Optional.of(token);
        } else {
            this.token = Optional.empty();
        }
    }

    /**
     * Constructor without token. Used while the token is unknown
     * @param symbol the symbol of the node
     */
    public Node(Symbol symbol) {
        this.symbol = symbol;
        this.scope = null;
        symtabIndex = -1;
        this.token = Optional.empty();
    }

    /**
     * @return the symbol of this node
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * sets the symbol to something else. used during the conversion to AST.
     * @param symbol the new symbol
     */
    protected void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Sets the token of the node
     * @param token the new token
     */
    public void setToken(Token token) {
        this.token = Optional.of(token);
    }

    /**
     * @return the token that created this node
     */
    public Token getToken() {
        return token.orElse(null);
    }

    /**
     * @return true if this is a disappearing node
     */
    public abstract boolean isEpsilon();

    public void setScope(SctNode scopeNode, int index) {
        scope = scopeNode;
        symtabIndex = index;
    }

    public void setScope(SctNode scope) {
        this.scope = scope;
        symtabIndex = -1;
    }

    public SymtabEntry getSymtabEntry() {
        if (symtabIndex == -1) {
            throw new NoSuchElementException();
        }
        return scope.getSymtab().get(symtabIndex);
    }

    public boolean hasScope() {
        return scope != null;
    }
}
