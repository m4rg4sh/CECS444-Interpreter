package Interpreter.Symtab;

import Tokens.Token;
import Tree.Ast.Node;

public class SymtabEntry {
    private Node idNode;
    private String identifier;
    private Object data;
    /*TODO Each variable can have its box as a slot directly in its symtab entry in its SCT node, or provide a reference to box which might
    be hosted elsewhere in your interpreter's heap memory. Note that as we have several data types */

    public SymtabEntry(Node idNode, Token token) {
        this.idNode = idNode;
        identifier = extractIdentifier(token);
    }

    public SymtabEntry(Token token) {
        this(null,token);
    }

    public Node getIdNode() {
        return idNode;
    }

    public String getIdentifier () {
        return identifier;
    }

    private String extractIdentifier (Token token) {
        return token.getCodeString();
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
