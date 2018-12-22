package Interpreter.Symtab;

import Tokens.Token;
import Tree.Ast.Node;

/**
 * This class represents an entry in a SymbolTable of the Interpreter
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class SymtabEntry {
    private Node idNode; // the AST node that created the entry (the corresponding identifier)
    private String identifier; // the name of the identifier
    private Object value;

    public SymtabEntry(Node idNode, Token token) {
        this.idNode = idNode;
        identifier = extractIdentifier(token);
        value = null;
    }


    public SymtabEntry(Token token) {
        this(null,token);
    }

    /**
     * @return the ast node that created this entry
     */
    public Node getIdNode() {
        return idNode;
    }

    /**
     * @return the identifier as string
     */
    public String getIdentifier () {
        return identifier;
    }

    /**
     * @param token a ID token
     * @return the name of the identifier as string
     */
    private String extractIdentifier (Token token) {
        return token.getCodeString();
    }

    /**
     * @return the current value of this symtab entry
     */
    public Object getValue() {
        return value;
    }

    /**
     * sets the value of this entry to a given value
     * @param o the new value
     */
    public void setValue(Object o) {
        value = o;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof SymtabEntry) {
            return o.hashCode() == this.hashCode();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }
}
