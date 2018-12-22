package Interpreter;

import Symbols.NonTerminal;
import Symbols.Terminal;
import Interpreter.Symtab.SymtabEntry;
import Tokens.Token;
import Tree.Ast.*;
import Tree.Sct.*;

/**
 * This class contains the methods needed to walk an AST and build a SCT
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class SctBuilder {
    private SctRootNode rootNode;
    private GeneralSctNode currentNode;

    public SctBuilder() {
        rootNode = new SctRootNode();
        currentNode = rootNode;
    }

    public GeneralSctNode buildSct(Node astRootNode) {
        walkAst(astRootNode);
        return rootNode;
    }

    /**
     * recursively walks an AST to create the scope nodes
     * @param astNode the ast to be walked
     */
    private void walkAst(Node astNode) {
        handleNode(astNode);
        if (astNode instanceof InnerNode) {
            for(Node child : ((InnerNode) astNode).getChildren()) {
                walkAst(child);
            }
        }
        handleNode(astNode);
    }

    /**
     * Checks a AST node and creates/closes SCT nodes or adds symtab entries to the tree
     * @param astNode
     */
    private void handleNode(Node astNode) {
        if (isOpeningNode(astNode)) {
            currentNode = currentNode.openChildScope(astNode);
            astNode.setScope(currentNode);
        } else if (isNewDeclaration(astNode)) {
            if (astNode.getSymbol() == NonTerminal.FCNID) {
                GeneralSctNode parent = ((SctNode)currentNode).getParentNode();
                int index = parent.addSymbol(astNode);
                astNode.setScope(parent,index);
            } else {
                astNode.setScope(currentNode, currentNode.addSymbol(astNode));
            }
        } else if (isIdentifier(astNode.getToken())) {
            GeneralSctNode scopeNode = currentNode.getScope(astNode.getToken());
            int index = scopeNode.indexOf(astNode.getToken());
            astNode.setScope(scopeNode,index);
        } else if (isClosingNode(astNode)) {
            currentNode = ((SctNode)currentNode).getParentNode();
        }
    }

    /**
     * @param node an AST node
     * @return true if this node opens a new scope and thus a new SCT node
     */
    private boolean isOpeningNode(Node node) {
        int tokenIde = node.getToken().getId();
        Terminal symbol = Terminal.valueOf(tokenIde);
        return !node.hasScope() && symbol.createsScope();
    }

    /**
     * @param node an AST node
     * @return true if this node closes a existing scope
     */
    private boolean isClosingNode(Node node) {
        return node.hasScope() && node.getSymbol().createsScope();
    }

    /**
     * @param astNode a AST node
     * @return true if this node contains a new identifier that is not yet visible in the SCT
     */
    private boolean isNewDeclaration(Node astNode) {
        if (isIdentifier(astNode.getToken())) {
            return !currentNode.containsSymbol(astNode.getToken());
        }
        return false;
    }

    /**
     * @param token a token
     * @return true if the given token is an ID
     */
    private boolean isIdentifier(Token token) {
        return token.getId() == Terminal.ID.getId();
    }

    /**
     * prints the scope tree well formatted to the console.
     */
    public void printScopeTree() {
        GeneralSctNode printNode = rootNode;
        System.out.println("\nScope Tree:\n");
        while (true) {
            int index = 0;
            if (printNode instanceof SctNode) {
                System.out.println("\nScope: " + ((SctNode)printNode).getOpeningNode().getToken().getCodeString());
            } else {
                System.out.println("\nScope: Global");
            }
            for (SymtabEntry e : printNode) {
                System.out.println("[" + index++ + "] "
                        + "| ID: " + e.getIdentifier()
                        + " | Value: " + printValue(e.getValue()));
            }
            if (printNode.hasChild()) {
                printNode = printNode.getChildNode();
            } else {
                break;
            }
        }
    }

    /**
     * returns the string "null" if the symtab entry is null or returns its value as string
     * @param o the symtab value
     * @return the string
     */
    private String printValue(Object o) {
        if (null == o) {
            return "Null";
        } else {
            return o.toString();
        }
    }
}
