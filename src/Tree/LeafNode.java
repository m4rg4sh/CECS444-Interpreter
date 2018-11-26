package Tree;

import Symbols.Symbol;
import Tokens.Token;

/**
 * This is a node that contains terminals which are stored as tokens.
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */


public class LeafNode extends Node {

    /**
     * Constructor
     * @param symbol the symbol of the node
     * @param token the token which created this node
     */
    public LeafNode(Symbol symbol, Token token) {
        super(symbol,token);
    }

    /**
     * Constructor without token. Used while the token is still unknown.
     * @param symbol the symbol of the node
     */
    public LeafNode(Symbol symbol) {
        this(symbol, null);
    }

    /**
     * @return always returns false because a LeafNode can't disappear on its own
     */
    public boolean isEpsilon() {
        return false;
    }

    /**
     * @return A string representation of the node
     */
    @Override
    public String toString() {
        return "PstLeafNode{" +
                "symbol = " + getSymbol() +
                ", token=" + getToken() +
                '}';
    }
}
