package Tree.Sct;

import Interpreter.Symtab.SymtabEntry;
import Tokens.FloatToken;
import Tokens.IntToken;
import Tokens.Token;
import Tree.Ast.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This abstract class represents the general idea of a scope tree node
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public abstract class GeneralSctNode implements Iterable<SymtabEntry>{
    protected ArrayList<SymtabEntry> symtab;
    protected SctNode childNode;

    public GeneralSctNode() {
        symtab = new ArrayList<>();
        childNode = null;
    }


    /**
     * Adds a new identifier to this scope node's symtab and links it back to the AST node
     * @param idNode the AST node that contains the new symbol
     * @return the index of the new entry
     */
    public int addSymbol(Node idNode) {
        SymtabEntry newEntry = new SymtabEntry(idNode,idNode.getToken());
        symtab.add(newEntry);
        return symtab.indexOf(newEntry);
    }

    /**
     * Attaches a new child node to this node and links them bidirectionally
     * @param openingNode the AST node that opens the new scope
     * @return the new child scope node that has been created
     */
    public SctNode openChildScope(Node openingNode) {
        SctNode newScope = new SctNode(openingNode, this);
        childNode = newScope;
        return newScope;
    }

    /**
     * @return the child node that follows this node
     */
    public GeneralSctNode getChildNode() {
        if (childNode != null) {
            return childNode;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * @return true if this node has a child node
     */
    public boolean hasChild() {
        return childNode != null;
    }

    /**
     * Traverses the SCT upwards and checks if the given identifier is already present in one of the symtabs
     * @param token the token that contains the identifier
     * @return true if the identifier is present in one of the symtabs
     */
    public abstract boolean containsSymbol(Token token);


    /**
     * Traverses the SCT upwards looking for a symtab that contains the given identifier
     * @param token the token that contains the identifier key
     * @return a reference to the node that contains the identifer
     */
    public abstract GeneralSctNode getScope(Token token);

    /**
     * Finds the index of a identifier token in this nodes symtab
     * @param token the token that contains the identifier key
     * @return the index in the symtab
     */
    public int indexOf(Token token) {
        SymtabEntry testEntry = new SymtabEntry(token);
        return symtab.indexOf(testEntry);
    }

    public SymtabEntry getSymtabEntry(int index) {
        return symtab.get(index);
    }

    /**
     * @return An iterator over the node's symtab
     */
    public Iterator<SymtabEntry> iterator() {
        return symtab.iterator();
    }
}
