package PST;

import Symbols.Symbol;

import java.util.ArrayList;
import java.util.List;

public class PstInnerNode extends PstNode {
    private ArrayList<PstNode> children;
    private int rule;

    public PstInnerNode(Symbol symbol, int ruleID) {
        super(symbol);
        this.rule = ruleID;
        children = new ArrayList<>();
    }

    public void addChild(PstNode child) {
        children.add(child);
    }

    public int getRuleId() {
        return rule;
    }
    public List<PstNode> getChildren() {
        return children;
    }
    
    @Override
    public String toString() {
        return "PstInnerNode{" +
                "symbol=" + getSymbol() +
                ", children=" + children +
                ", rule=" + rule +
                '}';
    }
}
