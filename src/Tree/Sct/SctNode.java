package Tree.Sct;

import Interpreter.Symtab.SymtabEntry;
import Tokens.Token;
import Tree.Ast.Node;

/**
 * This subclass represents a scope tree node that has a parent and links back to an AST node
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class SctNode extends GeneralSctNode {
    private GeneralSctNode parentNode;
    private Node openingNode;

    public SctNode(Node openingNode, GeneralSctNode parentNode) {
        super();
        this.openingNode = openingNode;
        this.parentNode = parentNode;
    }

    /**
     * @return the parent node of this node
     */
    public GeneralSctNode getParentNode() {
        return parentNode;
    }

    /**
     * @return the AST node that created this scope
     */
    public Node getOpeningNode() {
        return openingNode;
    }

    /**
     * Traverses the SCT upwards and checks if the given identifier is already present in one of the symtabs
     * @param token the token that contains the identifier
     * @return true if the identifier is present in one of the symtabs
     */
    @Override
    public boolean containsSymbol(Token token) {
        SymtabEntry testEntry = new SymtabEntry(token);
        if (symtab.contains(testEntry)) {
            return true;
        } else {
            return parentNode.containsSymbol(token);
        }
    }

    /**
     * Traverses the SCT upwards looking for a symtab that contains the given identifier
     * @param token the token that contains the identifier key
     * @return a reference to the node that contains the identifer
     */
    @Override
    public GeneralSctNode getScope(Token token) {
        SymtabEntry testEntry = new SymtabEntry(token);
        if (symtab.contains(testEntry)) {
            return this;
        } else {
            return parentNode.getScope(token);
        }
    }

}
