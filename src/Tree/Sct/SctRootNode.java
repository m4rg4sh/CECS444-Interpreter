package Tree.Sct;

import Interpreter.Symtab.SymtabEntry;
import Tokens.Token;
import java.util.NoSuchElementException;

/**
 * This scope node class represents the root of the tree.
 * It has no parent and doesn't link to any AST node.
 * It is indented to be used as the global scope of a program.
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class SctRootNode extends GeneralSctNode {

    /**
     * This creates a root node
     */
    public SctRootNode() {
        super();
    }

    /**
     * Checks if the symtab already contains a given identifier
     * @param token the token that contains the identifier
     * @return true if the identifier is present in this nodes symtab
     */
    @Override
    public boolean containsSymbol(Token token) {
        SymtabEntry testEntry = new SymtabEntry(token);
        return symtab.contains(testEntry);
    }

    /**
     * Returns this scope if the given identifier is presend in the symtab
     * @param token the token that contains the identifier key
     * @return this scope node
     */
    @Override
    public GeneralSctNode getScope(Token token) {
        if (containsSymbol(token)) {
            return this;
        } else {
            throw new NoSuchElementException();
        }
    }
}
