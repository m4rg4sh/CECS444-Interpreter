package PST;

import Symbols.Symbol;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the node that contains a non-terminal
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class PstInnerNode extends PstNode {
    private ArrayList<PstNode> children;
    private int rule;
    /**
     * This boolean is used for PST to AST conversions as a flag to safely delete
     */
    private boolean isEpsilon;
    
    /**
     * Constructor
     * @param symbol The symbol to be stored
     * @param ruleID The rule id that created this node
     */
    public PstInnerNode(Symbol symbol, int ruleID) {
        super(symbol);
        this.rule = ruleID;
        children = new ArrayList<>();
        isEpsilon = false;
    }
    
    /**
     * Default Constructor
     * @param symbol The symbol to be stored
     */
    public PstInnerNode(Symbol symbol) {
        this(symbol, 0);
    }

    public void setEpsilon(boolean epsilon) {
        isEpsilon = epsilon;
    }

    public boolean isEpsilon() {
        return isEpsilon;
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


    public int getRuleId() {
        return rule;
    }
    public ArrayList<PstNode> getChildren() {
        return children;
    }

    public void copyFrom(PstNode sourceNode) {
        setSymbol(sourceNode.getSymbol());
        if (sourceNode.getToken() != null) {
            setToken(sourceNode.getToken());
        }
        if (sourceNode instanceof PstInnerNode) {
            children.addAll(((PstInnerNode) sourceNode).getChildren());
        }
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