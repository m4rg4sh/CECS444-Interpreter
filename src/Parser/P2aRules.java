package Parser;


import Symbols.Terminal;
import Tree.Ast.InnerNode;
import Tree.Ast.Node;

/**
 * This class holds all handlers to convert a node from PST to AST
 *
 * @author Kevin Bui <Kevinthuybui@gmail.com>
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 * @author Gabe Flores <rgabeflores@gmail.com>
 * @author Richard Salmeron <richard.salmeron@student.csulb.edu>
 */
public class P2aRules {

    private P2aRules() {
        //should never be called
    }

    // Pgm = kwdprog Vargroup Fcndefs Main
    public static void rule1(InnerNode node) {
        removeEpsilonKids(node);
        //new root is always the kwdprog
        hoistKid(0,node);
    }

    // Main = kwdmain BBlock
    public static void rule2(InnerNode node) {
        //new root is always kwdmain
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    // BBlock = brace1 Vargroup Stmts brace2
    public static void rule3(InnerNode node) {
        removeEpsilonKids(node);
        node.removeChild(node.getChildCount()-1);
        if (node.getChildCount() == 0) {
            node.setEpsilon(true);
        } else {
            hoistKid(0,node);
        }
    }

    // Vargroup = kwdvars PPvarlist
    public static void rule4(InnerNode node) {
        //kwdvar is always the new root
        hoistKid(0, node);
    }

    // Vargroup = eps
    public static void rule5(InnerNode node) {
        node.setEpsilon(true);
    }

    // PPvarlist = parens1 Varlist parens2
    public static void rule6(InnerNode node) {
        removeEpsilonKids(node);
        node.removeChild(node.getChildCount()-1);
        node.removeChild(0);
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // Varlist = Varitem semi Varlist
    public static void rule7(InnerNode node) {
        // remove the semi and move all children up to the parent
        removeEpsilonKids(node);
        node.removeChild(1);
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // Varlist = eps
    public static void rule8(InnerNode node) {
        node.setEpsilon(true);
    }

    // Varitem = Vardecl VaritemT
    public static void rule9(InnerNode node) {
        removeEpsilonKids(node);
        //If there is a equals, we need to hoist it. otherwise we just replace the node
        if (node.getChildCount() == 2) {
            hoistKid(1, node);
        } else {
            hoistKid(0,node);
        }
    }

    // Varitem = Classdecl
    public static void rule10(InnerNode node) {
        hoistKid(0, node);
    }

    // VaritemT = equal Varinit
    public static void rule11(InnerNode node) {
        removeEpsilonKids(node); //Varinit can be eps by association refer to rule 28
        hoistKid(0, node);
    }

    // VaritemT = eps
    public static void rule12(InnerNode node) {
        node.setEpsilon(true);
    }

    // Vardecl = Simplekind Varspec
    public static void rule13(InnerNode node) {
        hoistKid(1, node);
    }

    // Simplekind = Basekind
    public static void rule14(InnerNode node) {
        hoistKid(0, node);
    }

    // Simplekind = Classid
    public static void rule15(InnerNode node) {
        hoistKid(0, node);
    }

    // Basekind = int
    public static void rule16(InnerNode node) {
        hoistKid(0, node);
    }

    // Basekind = float
    public static void rule17(InnerNode node) {
        hoistKid(0, node);
    }

    // Basekind = string
    public static void rule18(InnerNode node) {
        hoistKid(0, node);
    }

    // Classid = id
    public static void rule19(InnerNode node) {
        hoistKid(0, node);
    }

    // Varspec = Varid VarspecT
    public static void rule20(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Varspec = Deref_id
    public static void rule21(InnerNode node) {
        hoistKid(0, node);
    }

    // VarspecT = KKint
    public static void rule22(InnerNode node) {
        hoistKid(0, node);
    }

    // VarspecT = eps
    public static void rule23(InnerNode node) {
        node.setEpsilon(true);
    }

    // Varid = id
    public static void rule24(InnerNode node) {
        hoistKid(0, node);
    }

    // KKint = bracket1 int bracket2
    public static void rule25(InnerNode node) {
        node.removeChild(node.getChildCount()-1);
        hoistKid(0, node);
    }

    // Deref_id = Deref id
    public static void rule26(InnerNode node) {
        hoistKid(0, node);
    }

    // Deref = aster
    public static void rule27(InnerNode node) {
        hoistKid(0, node);
    }

    // Varinit = Expr
    public static void rule28(InnerNode node) {
        removeEpsilonKids(node); // expr can be eps
        if (node.getChildCount()==0){
            node.setEpsilon(true);
        } else {
            hoistKid(0, node);
        }
    }

    // Varinit = BBexprs
    public static void rule29(InnerNode node) {
        hoistKid(0, node);
    }

    // BBexprs = brace1 BBexprsT
    public static void rule30(InnerNode node) {
        hoistKid(0, node);
    }

    // BBexprsT = Exprlist brace2
    public static void rule31(InnerNode node) {
        removeEpsilonKids(node);
        node.removeChild(node.getChildCount()-1);
        hoistKid(0, node);
    }

    // BBexprsT = brace2
    public static void rule32(InnerNode node) {
        node.setEpsilon(true);
    }

    // Exprlist = Expr Moreexprs
    public static void rule33(InnerNode node) {
        removeEpsilonKids(node); // both can be eps
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // Moreexprs = comma Exprlist
    public static void rule34(InnerNode node) {
        // we can just remove the comma and move the expressions up to the parent
        removeEpsilonKids(node);
        node.removeChild(0);
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // Moreexprs = eps
    public static void rule35(InnerNode node) {
        node.setEpsilon(true);
    }

    // Classdecl = kwdclass Classid ClassdeclT
    public static void rule36(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // ClassdeclT = eps
    public static void rule37(InnerNode node) {
        node.setEpsilon(true);
    }

    // ClassdeclT = Classmom Interfaces BBclassitems
    public static void rule38(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
        //TODO check this
    }

    // BBClassitems = brace1 Classitems brace2
    public static void rule39(InnerNode node) {
        removeEpsilonKids(node);
        node.removeChild(node.getChildCount()-1);
        hoistKid(0, node);
    }

    // Classmom = colon Classid
    public static void rule40(InnerNode node) {
        hoistKid(0, node);
    }

    // Classmom = eps
    public static void rule41(InnerNode node) {
        node.setEpsilon(true);
    }

    // Classitems = Classgroup Classitems
    public static void rule42(InnerNode node) {
        removeEpsilonKids(node); // both can be eps
        if (node.getChildCount()==0){
            node.setEpsilon(true);
        }
        else{
            hoistKid(0, node);
        }
    }

    // Classitems = eps
    public static void rule43(InnerNode node) {
        node.setEpsilon(true);
    }

    // Classgroup = Class_ctrl
    public static void rule44(InnerNode node) {
        hoistKid(0, node);
    }

    // Classgroup = Varlist
    public static void rule45(InnerNode node) {
        removeEpsilonKids(node);// eps by association
        if (node.getChildCount()==0){
            node.setEpsilon(true);
        }
        else{
            hoistKid(0, node);
        }
    }

    // Classgroup = Mddecls
    public static void rule46(InnerNode node) {
        removeEpsilonKids(node);//eps by association
        if (node.getChildCount()==0){
            node.setEpsilon(true);
        }
        else{
            hoistKid(0, node);
        }
    }

    // Class_ctrl = colon id // in {public, protected, private}
    public static void rule47(InnerNode node) {
        hoistKid(0, node);
    }

    // Interfaces = colon Classid Interfaces
    public static void rule48(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Interfaces = eps
    public static void rule49(InnerNode node) {
        node.setEpsilon(true);
    }

    // Mddecls = Mdheader Mddecls
    public static void rule50(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Mddecls = eps
    public static void rule51(InnerNode node) {
        node.setEpsilon(true);
    }

    // Mdheader = kwdfcn Md_id PParmlist Retkind
    public static void rule52(InnerNode node) {
        hoistKid(0, node);
    }

    // Md_id = Classid colon Fcnid
    public static void rule53(InnerNode node) {
        hoistKid(1, node);
    }

    // Fcndefs = Fcndef Fcndefs
    public static void rule54(InnerNode node) {
        removeEpsilonKids(node);
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // Fcndefs = eps
    public static void rule55(InnerNode node) {
        node.setEpsilon(true);
    }

    // Fcndef = Fcnheader BBlock
    public static void rule56(InnerNode node) {
        //extract fcn keyword and hoist it?

        hoistKid(0, node);
    }

    // Fcnheader = kwdfcn Fcnid PParmlist Retkind
    public static void rule57(InnerNode node) {
        hoistKid(0, node);
    }

    // Fcnid = id
    public static void rule58(InnerNode node) {
        hoistKid(0, node);
    }

    // Retkind = Simplekind
    public static void rule59(InnerNode node){
        hoistKid(0, node);
    }
    // PParmlist = parens1 PParmlistT
    public static void rule60(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // PParmlistT = Varspecs parens2
    public static void rule61(InnerNode node) {
        node.removeChild(node.getChildCount()-1);
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // PParmlistT = parens2
    public static void rule62(InnerNode node) {
        node.setEpsilon(true);
    }

    // Varspecs = Varspec More_varspecs
    public static void rule63(InnerNode node) {
        removeEpsilonKids(node);
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // More_varspecs = comma Varspecs
    public static void rule64(InnerNode node) {
        // we can just remove the comma and move the varspecs up to the parent
        removeEpsilonKids(node);
        node.removeChild(0);
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // More_varspecs = eps
    public static void rule65(InnerNode node) {
        node.setEpsilon(true);
    }

    // PPonly = parens1 parens2
    public static void rule66(InnerNode node) {
        node.removeChild(node.getChildCount()-1);
        hoistKid(0, node);
    }

    // Stmts = Stmt semi Stmts
    public static void rule67(InnerNode node) {
        removeEpsilonKids(node);
        node.removeChild(1);
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // Stmts = eps
    public static void rule68(InnerNode node) {
        node.setEpsilon(true);
    }

    // Stmt = Stasgn
    public static void rule69(InnerNode node) {
        // --- CROSSED OUT ---
    }

    // Stmt = Fcall
    public static void rule70(InnerNode node) {
        // --- CROSSED OUT ---
    }

    // Stmt = Stif
    public static void rule71(InnerNode node) {
        hoistKid(0, node);
    }

    // Stmt = Stwhile
    public static void rule72(InnerNode node) {
        hoistKid(0, node);
    }

    // Stmt = Stprint
    public static void rule73(InnerNode node) {
        hoistKid(0, node);
    }

    // Stmt = Strtn
    public static void rule74(InnerNode node) {
        hoistKid(0, node);
    }

    // Stmt = Deref_id equal Expr
    public static void rule75(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(1, node);
    }

    // Stmt = id StmtT
    public static void rule76(InnerNode node) {
        if (node.getChild(1).getToken().getId() == Terminal.EQUAL.getId()) {
            hoistKid(1, node);
        } else {
            hoistKid(0, node);
        }
    }

    // StmtT = LvalT StmtT
    public static void rule77(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // StmtT = PPexprs
    public static void rule78(InnerNode node) {
        hoistKid(0, node);
    }

    // Stasgn = Lval equal Expr
    public static void rule79(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(1, node);

        // --- CROSSED OUT ---
    }

    // Lval = id LvalT
    public static void rule80(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Lval = Deref_id
    public static void rule81(InnerNode node) {
        hoistKid(0, node);
    }

    // LvalT = eps
    public static void rule82(InnerNode node) {
        node.setEpsilon(true);
    }

    // LvalT = KKexpr
    public static void rule83(InnerNode node) {
        hoistKid(0, node);
    }

    // KKexpr = bracket1 Expr bracket2
    public static void rule84(InnerNode node) {
        removeEpsilonKids(node);
        node.removeChild(node.getChildCount()-1);
        hoistKid(0, node);
    }

    // Fcall = Fcnid PPexprs
    public static void rule85(InnerNode node) {
        // ---- CROSSED OUT ----
    }

    // PPexprs = parens1 PPexprsT
    public static void rule86(InnerNode node) {
        node.removeChild(0);
        hoistKid(0, node);
    }

    // PPexprsT = Exprlist parens2
    public static void rule87(InnerNode node) {
        removeEpsilonKids(node); // exprlist can be eps by association
        node.removeChild(node.getChildCount()-1);
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // PPexprsT = parens2
    public static void rule88(InnerNode node) {
        node.setEpsilon(true);
    }

    // Stif = kwdif PPexpr BBlock Elsepart
    public static void rule89(InnerNode node){
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    // Elsepart = kwdelseif PPexpr BBlock Elsepart
    public static void rule90(InnerNode node){
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Elsepart = kwdelse BBlock
    public static void rule91(InnerNode node){
        hoistKid(0, node);
    }

    // Elsepart = eps
    public static void rule92(InnerNode node){
        node.setEpsilon(true);
    }

    // Stwhile = kwdwhile PPexpr BBlock
    public static void rule93(InnerNode node){
        hoistKid(0,node);
    }

    // Stprint = kprint PPexprs
    public static void rule94(InnerNode node){
        hoistKid(0, node);
    }

    // Strtn = kwdreturn StrtnT
    public static void rule95(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    // StrtnT = Expr
    public static void rule96(InnerNode node) {
        removeEpsilonKids(node);// eps by association
        if (node.getChildCount()==0){
            node.setEpsilon(true);
        }
        else{
            hoistKid(0, node);
        }
    }

    // StrtnT = eps
    public static void rule97(InnerNode node) {
        node.setEpsilon(true);
    }

    // PPexpr = parens1 Expr parens2
    public static void rule98(InnerNode node) {
        removeEpsilonKids(node);
        node.removeChild(0);
        node.removeChild(node.getChildCount()-1);
        hoistKid(0,node);

    }

    // Expr = Oprel Rterm Expr
    public static void rule99(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);

    }

    // Expr = Rterm Expr
    public static void rule100(InnerNode node) {
        removeEpsilonKids(node);// eps by association
        if (node.getChildCount() > 1 &&
                (node.getChild(1).getToken().getId() == Terminal.PLUS.getId()
                        || node.getChild(1).getToken().getId() == Terminal.ANGLE1.getId()
                        || node.getChild(1).getToken().getId() == Terminal.ANGLE2.getId()
                        || node.getChild(1).getToken().getId() == Terminal.ASTER.getId()
                        || node.getChild(1).getToken().getId() == Terminal.EQUAL.getId()
                        || node.getChild(1).getToken().getId() == Terminal.MINUS.getId()
                )
        ) {
            hoistKid(1,node);
        } else {
            InnerNode parent = node.getParent();
            parent.injectChildren(node.getChildren(),node);
        }
    }

    // Expr = eps
    public static void rule101(InnerNode node) {
        node.setEpsilon(true);
    }

    // Rterm = Opadd Term Rterm
    public static void rule102(InnerNode node) {
        removeEpsilonKids(node);
        if (node.getChildCount()==2) {
            //Rterm -> eps, only Opadd and Term left
            hoistKid(0,node);
        } else {
            //Term and Rterm are present
            //we need to hoist $1 and move $2 down to $3
            ((InnerNode)node.getChild(2)).addChild(node.getChild(1),0);
            node.removeChild(1);
            hoistKid(0,node);
        }
    }

    // Rterm = Term Rterm
    public static void rule103(InnerNode node) {
        removeEpsilonKids(node);// eps by association
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // Rterm = eps
    public static void rule104(InnerNode node) {
        node.setEpsilon(true);
    }

    // Term = Opmul Factcheck Term
    public static void rule105(InnerNode node) {
        removeEpsilonKids(node);
        if (node.getChildCount() == 0) {
            node.setEpsilon(true);
        } else {
            hoistKid(0, node);
        }

    }

    // Term = Fact Term
    public static void rule106(InnerNode node) {
        removeEpsilonKids(node);
        InnerNode parent = node.getParent();
        parent.injectChildren(node.getChildren(),node);
    }

    // Term = eps
    public static void rule107(InnerNode node) {
        node.setEpsilon(true);
    }

    // Factcheck = Fact
    public static void rule108(InnerNode node) {
        removeEpsilonKids(node);
        if (node.getChildCount() == 0) {
         node.setEpsilon(true);
        } else {
            hoistKid(0, node);
        }
    }

    // Factcheck = Deref_id
    public static void rule109(InnerNode node) {
        hoistKid(0,node);
    }

    // Fact = Basekind
    public static void rule110(InnerNode node) {
        hoistKid(0,node);
    }

    // Fact = Addrof_id
    public static void rule111(InnerNode node) {
        hoistKid(0,node);
    }

    // Fact = PPexpr
    public static void rule112(InnerNode node) {
        hoistKid(0,node);
    }

    // Fact = id FactT
    public static void rule113(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);

    }

    // FactT = PPexprs
    public static void rule114(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    // FactT = KKexpr
    public static void rule115(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    // FactT = eps
    public static void rule116(InnerNode node) {
        node.setEpsilon(true);
    }

    // Addrof_id = ampersand id
    public static void rule117(InnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = opeq
    public static void rule118(InnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = opne
    public static void rule119(InnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = Lthan
    public static void rule120(InnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = ople
    public static void rule121(InnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = opge
    public static void rule122(InnerNode node) {
        hoistKid(0,node);
    }

    // Oprel = Gthan
    public static void rule123(InnerNode node) {
        hoistKid(0,node);
    }

    // Lthan = angle1
    public static void rule124(InnerNode node) {
        hoistKid(0,node);
    }

    // Gthan = angle2
    public static void rule125(InnerNode node) {
        hoistKid(0,node);
    }

    // Opadd = plus
    public static void rule126(InnerNode node) {
        hoistKid(0,node);
    }

    // Opadd = minus
    public static void rule127(InnerNode node) {
        hoistKid(0,node);
    }

    // Opmul = aster
    public static void rule128(InnerNode node) {
        hoistKid(0,node);
    }

    // Opmul = slash
    public static void rule129(InnerNode node) {
        hoistKid(0,node);
    }

    // Opmul = caret
    public static void rule130(InnerNode node) {
        hoistKid(0,node);
    }

    // StmtT = equal Expr
    public static void rule131(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    // RVAL = INT
    public static void rule132(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    // RVAL = FLOAT
    public static void rule133(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    // RVAL = STRING
    public static void rule134(InnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    private static void removeEpsilonKids(InnerNode node) {
        node.getChildren().removeIf(Node::isEpsilon);
    }

    private static void hoistKid(int id, InnerNode node) {
        Node kid = node.getChild(id);
        node.copyFrom(kid);
        node.removeChild(kid);

    }
}
