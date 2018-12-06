package Interpreter;

import Symbols.NonTerminal;
import Symbols.Terminal;
import Interpreter.Symtab.SymtabEntry;
import Tokens.Token;
import Tree.Ast.*;
import Tree.Sct.*;

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

    private void walkAst(Node astNode) {
        handleNode(astNode);
        if (astNode instanceof InnerNode) {
            for(Node child : ((InnerNode) astNode).getChildren()) {
                walkAst(child);
            }
        }
        handleNode(astNode);
    }

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

    private boolean isOpeningNode(Node node) {
        int tokenIde = node.getToken().getId();
        Terminal symbol = Terminal.valueOf(tokenIde);
        return !node.hasScope() && symbol.createsScope();
    }

    private boolean isClosingNode(Node node) {
        return node.hasScope() && node.getSymbol().createsScope();
    }

    private boolean isNewDeclaration(Node astNode) {
        if (isIdentifier(astNode.getToken())) {
            return !currentNode.containsSymbol(astNode.getToken());
        }
        return false;
    }

    private boolean isIdentifier(Token token) {
        return token.getId() == Terminal.ID.getId();
    }

    public void printScopeTree() {
        GeneralSctNode printNode = rootNode;
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

    private String printValue(Object o) {
        if (null == o) {
            return "Null";
        } else {
            return o.toString();
        }
    }
}
