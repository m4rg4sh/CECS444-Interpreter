package Tree.Ast;

import Interpreter.Symtab.SymtabEntry;
import Symbols.Symbol;
import Tokens.Token;
import Tree.Sct.GeneralSctNode;

import java.util.Optional;

/**
 * This is the abstract Class for all tree nodes. Used to create the PST and AST.
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public abstract class Node {
    private Symbol symbol;
    private Optional<Token> token;
    private GeneralSctNode scope;
    private int symtabIndex;
    protected InnerNode parent;

    public Node(Symbol symbol, Token token, InnerNode parent) {
        this.symbol = symbol;
        this.scope = null;
        symtabIndex = -1;
        if (null != token) {
            this.token = Optional.of(token);
        } else {
            this.token = Optional.empty();
        }
        this.parent = parent;
    }
    /**
     * Constructor
     * @param symbol the symbol of the node
     * @param token the token that created the node
     */
    public Node(Symbol symbol, Token token) {
        this(symbol,token,null);
    }

    public Node(Symbol symbol, InnerNode parent) {
        this(symbol,null,parent);
    }

    /**
     * Constructor without token. Used while the token is unknown
     * @param symbol the symbol of the node
     */
    public Node(Symbol symbol) {
        this(symbol,null,null);
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

    /**
     * Links this node to a scope in the scope tree and sets the index where we can find the identifer in the symtab
     * @param scopeNode the scope node to link to
     * @param index the index of the identifier in the symbtab
     */
    public void setScope(GeneralSctNode scopeNode, int index) {
        scope = scopeNode;
        symtabIndex = index;
    }

    /**
     * Links this node to a scope in the scope tree
     * @param scope the scope node to link to
     */
    public void setScope(GeneralSctNode scope) {
        setScope(scope,-1);
    }


    /**
     * @return true if this node links to a scope node
     */
    public boolean hasScope() {
        return scope != null;
    }

    public InnerNode getParent() {
        return parent;
    }

    public SymtabEntry getSymtabEntry() {
        return scope.getSymtabEntry(symtabIndex);
    }
}
