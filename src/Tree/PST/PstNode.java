package Tree.PST;

import Symbols.Symbol;
import Tree.TreeNode;

public abstract class PstNode implements TreeNode {
    private Symbol symbol;

    //TODO store the rule that was used

    public PstNode(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
