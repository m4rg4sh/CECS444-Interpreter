package Tree;

import Symtab.SymtabEntry;
import Tokens.Token;

import java.util.ArrayList;
import java.util.List;


public class SctNode {
    private ArrayList<SymtabEntry> symtab;
    private SctNode parentNode;
    private SctNode childNode;
    private Node openingNode;

    public SctNode(Node openingNode, SctNode parentNode) {
        this.openingNode = openingNode;
        this.parentNode = parentNode;
        symtab = new ArrayList<>();
        childNode = null;
    }

    public SctNode() {
        this(null,null);
    }

    public int addSymbol(Node idNode) {
        SymtabEntry newEntry = new SymtabEntry(idNode, idNode.getToken());
        symtab.add(newEntry);
        return symtab.indexOf(newEntry);
    }

    public SctNode openChildScope(Node openingNode) {
        SctNode newScope = new SctNode(openingNode, this);
        childNode = newScope;
        return newScope;
    }

    public SctNode getParentNode() {
        return parentNode;
    }

    public SctNode getChildNode() {
        return childNode;
    }

    public Node getOpeningNode() {
        return openingNode;
    }

    public List<SymtabEntry> getSymtab() {
        return symtab;
    }

    public boolean containsSymbol(Token token) {
        SymtabEntry testEntry = new SymtabEntry(token);
        if (symtab.contains(testEntry)) {
            return true;
        } else {
            if (parentNode != null) {
                return parentNode.containsSymbol(token);
            } else {
                return false;
            }
        }
    }
}
