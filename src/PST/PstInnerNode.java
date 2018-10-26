package PST;

import Symbols.Symbol;

import java.util.ArrayList;
import java.util.List;

public class PstInnerNode extends PstNode {
    private ArrayList<PstNode> children;

    public PstInnerNode(Symbol symbol) {
        super(symbol);
        children = new ArrayList<>();
    }

    public void addChild(PstNode child) {
        children.add(child);
    }

    public List<PstNode> getChildren() {
        return children;
    }
}
