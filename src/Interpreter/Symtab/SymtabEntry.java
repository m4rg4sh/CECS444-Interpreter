package Interpreter.Symtab;

import Tokens.Token;
import Tree.Ast.Node;

public class SymtabEntry {
    private Node idNode;
    private String identifier;
    private Object value;

    public SymtabEntry(Node idNode, Token token) {
        this.idNode = idNode;
        identifier = extractIdentifier(token);
        value = null;
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

    public Object getValue() {
        return value;
    }

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
