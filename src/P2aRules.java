import PST.*;

public class P2aRules {

    private P2aRules() {
        //should never be called
    }

    // Pgm = kwdprog Vargroup Fcndefs Main
    public static void rule1(PstInnerNode node) {
        // kwdprog AKA kid1 is the new parent
        PstNode kid1 = node.getChild(0);
        node.copyFrom(kid1);

        removeEpsilonKids(node);
        node.removeChild(kid1);
    }

    // Main = kwdmain BBlock
    public static void rule2(PstInnerNode node) {
        // kwdmain AKA kid1 is the new parent
        PstNode kid1 = node.getChild(0);
        node.copyFrom(kid1);
        node.removeChild(kid1);
    }

    // BBlock = brace1 Vargroup Stmts brace2
    public static void rule3(PstInnerNode node) {
        // brace1 AKA kid1 is the new parent
        PstNode kid1 = node.getChild(0);
        node.copyFrom(kid1);

        removeEpsilonKids(node);

        node.removeChild(node.getChildCount()-1); //remove brace2 which is always the last child
        node.removeChild(kid1);
    }

    // Vargroup = eps
    public static void rule5(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // PPvarlist = parens1 Varlist parens2
    public static void rule6(PstNode node) {
        //TODO implement this
    }

    // Fcndefs = eps
    public static void rule55(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Stmts = eps
    public static void rule68(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // StmtT = equal Expr
    public static void rule131(PstInnerNode node) {
        // equal AKA kid1 is the new parent
        PstLeafNode kid1 = (PstLeafNode) node.getChild(0); //equal
        removeEpsilonKids(node);

        //hoist kid1
        node.copyFrom(kid1);
        node.removeChild(kid1);
    }

    private static void removeEpsilonKids(PstInnerNode node) {
        node.getChildren().removeIf(PstNode::isEpsilon);
    }
}
