import PST.PstInnerNode;
import PST.PstNode;

public class P2aRules {

    // Pgm = kwdprog Vargroup Fcndefs Main
    public static void rule1(PstInnerNode node) {
        // kwdprog AKA kid1 is the new parent
        PstNode kid1 = node.getChild(0);
        node.copyFrom(kid1);

        //check if Vargroup and/or Fcndefs are eps and remove them if so
        PstNode kid2 = node.getChild(1);
        PstNode kid3 = node.getChild(2);
        if (((PstInnerNode) kid2).getChildCount() == 0) {
            node.removeChild(kid2);
        }
        if (((PstInnerNode) kid3).getChildCount() == 0) {
            node.removeChild(kid3);
        }

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

        //check if Vargroup and/or Stmts are eps and remove them if so
        PstNode kid2 = node.getChild(1);
        PstNode kid3 = node.getChild(2);
        if (((PstInnerNode) kid2).getChildCount() == 0) {
            node.removeChild(kid2);
        }
        if (((PstInnerNode) kid3).getChildCount() == 0) {
            node.removeChild(kid3);
        }

        node.removeChild(node.getChildCount()-1); //remove brace2 which is always the last child
        node.removeChild(kid1);
    }

    // Vargroup = eps
    public static void rule5(PstNode node) {
        //do nothing, this node will be removed by parent rule
    }

    // PPvarlist = parens1 Varlist parens2
    public static void rule6(PstNode node) {
        //TODO implement this
    }

    // Fcndefs = eps
    public static void rule55(PstNode node) {
        //do nothing, this node will be removed by parent rule
    }

    // Stmts = eps
    public static void rule68(PstNode node) {
        //do nothing, this node will be removed by parent rule
    }
}
