package Symtab;

import Tokens.Token;
import Tree.Node;

public class SymtabEntry {
    private Node idNode;
    private String identifier;

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
