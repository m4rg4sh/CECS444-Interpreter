package Tree.Ast;

import Symbols.Symbol;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a tree node that has children.
 * In the PST it is used for Non Terminals but it will hold Terminals once it's been converted to an AST
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class InnerNode extends Node {
    private ArrayList<Node> children;
    private int rule;

    /**
     * This boolean is used for Tree to AST conversions as a flag to safely delete
     */
    private boolean isEpsilon;
    
    /**
     * Constructor
     * @param symbol The symbol to be stored
     * @param ruleID The rule id that created this node
     */
    public InnerNode(Symbol symbol, int ruleID, InnerNode parent) {
        super(symbol,parent);
        this.rule = ruleID;
        children = new ArrayList<>();
        isEpsilon = false;
    }
    
    /**
     * Constructor without rule ID if it's not available
     * @param symbol The symbol to be stored
     */
    public InnerNode(Symbol symbol,InnerNode parent) {
        this(symbol, 0,parent);
    }

    /**
     * Epsilon Nodes are disappearing and will be deleted during the parsing
     * @param epsilon
     */
    public void setEpsilon(boolean epsilon) {
        isEpsilon = epsilon;
    }

    /**
     * @return true if this is a disappearing node
     */
    public boolean isEpsilon() {
        return isEpsilon;
    }

    public void addChild(Node child, int index) {
        children.add(index, child);
    }

    /**
     * Adds a list of nodes as children to this node
     * @param nodes the new children
     */
    public void addChildren(List<Node> nodes) {
        children.addAll(nodes);
    }
    /**
     * @return The number of children this node has
     */
    public int getChildCount() {
        return children.size();
    }

    /**
     * Returns a child specified by its index
     * @param index the index of the child
     * @return the selected child node
     */
    public Node getChild(int index) {
        return children.get(index);
    }

    /**
     * Removes a child from this node
     * @param child the child to be removed
     */
    public void removeChild(Node child) {
        children.remove(child);
    }

    public void removeChild(int index) {children.remove(index);}

    /**
     * Sets the rule id used to build the children of this node
     * @param id the rule id
     */
    public void setRule(int id) {
        rule = id;
    }

    /**
     * @return the rule id used to build the children of this node
     */
    public int getRuleId() {
        return rule;
    }

    /**
     * @return An ArrayList with all the children of this node
     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    public void injectChildren(List<Node> newChildren, Node replacedChild) {
        int position = children.indexOf(replacedChild);
        for (Node c : newChildren) {
            children.add(position++, c);
        }
        children.remove(replacedChild);
    }

    /**
     * Copied the contents ("guts") of a different tree node into this one
     * @param sourceNode
     */
    public void copyFrom(Node sourceNode) {
        int index = children.indexOf(sourceNode);
        //TODO maybe revert this...
        //setSymbol(sourceNode.getSymbol());
        if (sourceNode.getToken() != null) {
            setToken(sourceNode.getToken());
        }
        if (sourceNode instanceof InnerNode) {
            children.addAll(index,((InnerNode) sourceNode).getChildren());
        }
        parent = sourceNode.getParent();
    }

    /**
     * @return a string representation of this node
     */
    @Override
    public String toString() {
        return "InnerNode{" +
                "symbol=" + getSymbol() +
                ", children=" + children +
                ", rule=" + rule +
                '}';
    }
}
