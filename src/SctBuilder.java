import Symbols.Terminal;
import Symtab.SymtabEntry;
import Tokens.Token;
import Tree.InnerNode;
import Tree.Node;
import Tree.SctNode;

public class SctBuilder {
    private SctNode rootNode;
    private SctNode currentNode;

    public SctBuilder() {
        rootNode = new SctNode();
        currentNode = rootNode;
    }

    public SctNode buildSct(Node astRootNode) {
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
            astNode.setScope(currentNode,currentNode.addSymbol(astNode));
        } else if (isIdentifier(astNode.getToken())) {
            //TODO add handler for an existing symbol, I don't know what to do here
        } else if (isClosingNode(astNode)) {
            currentNode = currentNode.getParentNode();
        }
    }

    private boolean isOpeningNode(Node node) {
        return !node.hasScope() && node.getSymbol().createsScope();
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
        SctNode printNode = rootNode;
        while (printNode != null) {
            int index = 0;
            for (SymtabEntry e : printNode.getSymtab()) {
                System.out.println("[" + index++ + "] | ID: " + e.getIdentifier() + " | Opened by: " + e.getIdNode().getToken().toTreeString());
            }
            printNode = printNode.getChildNode();
        }
    }
}
