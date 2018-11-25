import PST.*;

public class P2aRules {

    private P2aRules() {
        //should never be called
    }

    // Pgm = kwdprog Vargroup Fcndefs Main
    public static void rule1(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    // Main = kwdmain BBlock
    public static void rule2(PstInnerNode node) {
        hoistKid(0,node);
    }

    // BBlock = brace1 Vargroup Stmts brace2
    public static void rule3(PstInnerNode node) {
        removeEpsilonKids(node);
        node.removeChild(node.getChildCount()-1); //remove brace2 which is always the last child
        hoistKid(0,node);
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

    // FactT = PPexprs
    public static void rule114(PstInnerNode node) {
        removeEpsilonKids(node);
        node.removeChild(node.getChildCount()-1); //remove brace2 which is always the last child
        hoistKid(0,node);
    }

    // FactT = KKexpr
    public static void rule115(PstInnerNode node) {
        removeEpsilonKids(node);
        node.removeChild(node.getChildCount()-1); //remove bracket2 which is always the last child
        hoistKid(0,node);
    }

    // FactT = eps
    public static void rule116(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Addrof_id = ampersand id
    public static void rule117(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = opeq
    public static void rule118(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = opne
    public static void rule119(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = Lthan
    public static void rule120(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = ople
    public static void rule121(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = opge
    public static void rule122(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = Gthan
    public static void rule123(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Lthan = angle1
    public static void rule124(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Gthan = angle2
    public static void rule125(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Opadd = plus
    public static void rule126(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Opadd = minus
    public static void rule127(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Opmul = aster
    public static void rule128(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Opmul = slash
    public static void rule129(PstInnerNode node) {
        hoistKid(0,node);
    }

    // Opmul = caret
    public static void rule130(PstInnerNode node) {
        hoistKid(0,node);
    }

    // StmtT = equal Expr
    public static void rule131(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    private static void removeEpsilonKids(PstInnerNode node) {
        node.getChildren().removeIf(PstNode::isEpsilon);
    }

    private static void hoistKid(int id, PstInnerNode node) {
        PstNode kid = node.getChild(id);
        node.copyFrom(kid);
        node.removeChild(kid);

    }
}
