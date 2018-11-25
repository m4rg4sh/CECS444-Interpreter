package PST;

import Symbols.Symbol;
import java.util.ArrayList;
import java.util.List;

public class PstInnerNode extends PstNode {
    private ArrayList<PstNode> children;
    private int rule;
    private boolean isEpsilon;

    public void setEpsilon(boolean epsilon) {
        isEpsilon = epsilon;
    }

    public PstInnerNode(Symbol symbol, int ruleID) {
        super(symbol);
        this.rule = ruleID;
        children = new ArrayList<>();
        isEpsilon = false;
    }

    public PstInnerNode(Symbol symbol) {
        this(symbol, 0);
    }

    public void addChild(PstNode child) {
        children.add(child);
    }

    public int getChildCount() {
        return children.size();
    }

    public PstNode getChild(int id) {
        return children.get(id);
    }

    public void removeChild(PstNode child) {
        children.remove(child);
    }

    public void removeChild(int id) {
        children.remove(id);
    }

    public void setRule(int id) {
        rule = id;
    }

    public boolean isEpsilon() {
        return isEpsilon;
    }

    public int getRuleId() {
        return rule;
    }
    public List<PstNode> getChildren() {
        return children;
    }

    public void copyFrom(PstNode sourceNode) {
        setSymbol(sourceNode.getSymbol());
        //TODO potentially copy other stuff that isn't implemented yet
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
