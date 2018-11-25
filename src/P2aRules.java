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

    // Vargroup = kwdvars PPvarlist
    public static void rule4(PstInnerNode node) {

    }

    // Vargroup = eps
    public static void rule5(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // PPvarlist = parens1 Varlist parens2
    public static void rule6(PstInnerNode node) {

    }

    // Varlist = Varitem semi Varlist
    public static void rule7(PstInnerNode node) {

    }

    // Varlist = eps
    public static void rule8(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Varitem = Vardecl VaritemT
    public static void rule9(PstInnerNode node) {

    }

    // Varitem = Classdecl
    public static void rule10(PstInnerNode node) {

    }

    // VaritemT = equal Varinit
    public static void rule11(PstInnerNode node) {

    }

    // VaritemT = eps
    public static void rule12(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Vardecl = Simplekind Varspec
    public static void rule13(PstInnerNode node) {

    }

    // Simplekind = Basekind
    public static void rule14(PstInnerNode node) {

    }

    // Simplekind = Classid
    public static void rule15(PstInnerNode node) {

    }

    // Basekind = int
    public static void rule16(PstInnerNode node) {

    }

    // Basekind = float
    public static void rule17(PstInnerNode node) {

    }

    // Basekind = string
    public static void rule18(PstInnerNode node) {

    }

    // Classid = id
    public static void rule19(PstInnerNode node) {

    }

    // Varspec = Varid VarspecT
    public static void rule20(PstInnerNode node) {

    }

    // Varspec = Deref_id
    public static void rule21(PstInnerNode node) {

    }

    // VarspecT = KKint
    public static void rule22(PstInnerNode node) {

    }

    // VarspecT = eps
    public static void rule23(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Varid = id
    public static void rule24(PstInnerNode node) {

    }

    // KKint = bracket1 int bracket2
    public static void rule25(PstInnerNode node) {

    }

    // Deref_id = Deref id
    public static void rule26(PstInnerNode node) {

    }

    // Deref = aster
    public static void rule27(PstInnerNode node) {

    }

    // Varinit = Expr
    public static void rule28(PstInnerNode node) {

    }

    // Varinit = BBexprs
    public static void rule29(PstInnerNode node) {

    }

    // BBexprs = brace1 BBexprsT
    public static void rule30(PstInnerNode node) {

    }

    // BBexprsT = Exprlist brace2
    public static void rule31(PstInnerNode node) {

    }

    // BBexprsT = brace2
    public static void rule32(PstInnerNode node) {

    }

    // Exprlist = Expr Moreexprs
    public static void rule33(PstInnerNode node) {

    }

    // Moreexprs = comma Exprlist
    public static void rule34(PstInnerNode node) {

    }

    // Moreexprs = eps
    public static void rule35(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Classdecl = kwdclass Classid ClassdeclT
    public static void rule36(PstInnerNode node) {

    }

    // ClassdeclT = eps
    public static void rule37(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // ClassdeclT = Classmom Interfaces BBclassitems
    public static void rule38(PstInnerNode node) {

    }

    // BBClassitems = brace1 Classitems brace2
    public static void rule39(PstInnerNode node) {

    }

    // Classmom = colon Classid
    public static void rule40(PstInnerNode node) {

    }

    // Classmom = eps
    public static void rule41(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Classitems = Classgroup Classitems
    public static void rule42(PstInnerNode node) {

    }

    // Classitems = eps
    public static void rule43(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Classgroup = Class_ctrl
    public static void rule44(PstInnerNode node) {

    }

    // Classgroup = Varlist
    public static void rule45(PstInnerNode node) {

    }

    // Classgroup = Mddecls
    public static void rule46(PstInnerNode node) {

    }

    // Class_ctrl = colon id // in {public, protected, private}
    public static void rule47(PstInnerNode node) {

    }

    // Interfaces = colon Classid Interfaces
    public static void rule48(PstInnerNode node) {

    }

    // Interfaces = eps
    public static void rule49(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Mddecls = Mdheader Mddecls
    public static void rule50(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Mddecls = eps
    public static void rule51(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Mdheader = kwdfcn Md_id PParmlist Retkind
    public static void rule52(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Md_id = Classid colon Fcnid
    public static void rule53(PstInnerNode node) {
        hoistKid(1, node); // Is this right?
    }

    // Fcndefs = Fcndef Fcndefs
    public static void rule54(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Fcndefs = eps
    public static void rule55(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Fcndef = Fcnheader BBlock
    public static void rule56(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Fcnheader = kwdfcn Fcnid PParmlist Retkind
    public static void rule57(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Fcnid = id
    public static void rule58(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Retkind = Simplekind
    public static void rule59(PstInnerNode node){
        hoistKid(0, node);
    }
    // PParmlist = parens1 PParmlistT
    public static void rule60(PstInnerNode node) {
        hoistKid(0, node);
    }

    // PParmlistT = Varspecs parens2
    public static void rule61(PstInnerNode node) {
        hoistKid(0, node);
    }

    // PParmlistT = parens2
    public static void rule62(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Varspecs = Varspec More_varspecs
    public static void rule63(PstInnerNode node) {
        hoistKid(0, node);
    }

    // More_varspecs = comma Varspecs
    public static void rule64(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // More_varspecs = eps
    public static void rule65(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // PPonly = parens1 parens2
    public static void rule66(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Stmts = Stmt semi Stmts
    public static void rule67(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(1, node);
    }

    // Stmts = eps
    public static void rule68(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // Stmt = Stasgn
    public static void rule69(PstInnerNode node) {
        // --- CROSSED OUT ---
    }

    // Stmt = Fcall
    public static void rule70(PstInnerNode node) {
        // --- CROSSED OUT ---
    }

    // Stmt = Stif
    public static void rule71(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Stmt = Stwhile
    public static void rule72(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Stmt = Stprint
    public static void rule73(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Stmt = Strtn
    public static void rule74(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Stmt = Deref_id equal Expr
    public static void rule75(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(1, node);
    }

    // Stmt = id StmtT
    public static void rule76(PstInnerNode node) {
        hoistKid(0, node);
    }

    // StmtT = LvalT StmtT
    public static void rule77(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // StmtT = PPexprs
    public static void rule78(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Stasgn = Lval equal Expr
    public static void rule79(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(1, node);
    }

    // Lval = id LvalT
    public static void rule80(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Lval = Deref_id
    public static void rule81(PstInnerNode node) {
        hoistKid(0, node);
    }

    // LvalT = eps
    public static void rule82(PstInnerNode node) {
        node.setEpsilon(true);
    }

    // LvalT = KKexpr
    public static void rule83(PstInnerNode node) {
        hoistKid(0, node);
    }

    // KKexpr = bracket1 Expr bracket2
    public static void rule84(PstInnerNode node) {
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Fcall = Fcnid PPexprs
    public static void rule85(PstInnerNode node) {
        // ---- CROSSED OUT ----
    }

    // PPexprs = parens1 PPexprsT
    public static void rule86(PstInnerNode node) {
        hoistKid(0, node);
    }

    // PPexprsT = Exprlist parens2
    public static void rule87(PstInnerNode node) {
        hoistKid(0, node);
    }

    // PPexprsT = parens2
    public static void rule88(PstInnerNode node) {
        hoistKid(0, node);
    }

    // Stif = kwdif PPexpr BBlock Elsepart
    public static void rule89(PstInnerNode node){
        removeEpsilonKids(node);
        hoistKid(0,node);
    }

    // Elsepart = kwdelseif PPexpr BBlock Elsepart
    public static void rule90(PstInnerNode node){
        removeEpsilonKids(node);
        hoistKid(0, node);
    }

    // Elsepart = kwdelse BBlock
    public static void rule91(PstInnerNode node){
        hoistKid(0, node);
    }

    // Elsepart = eps
    public static void rule92(PstInnerNode node){
        node.setEpsilon(true);
    }

    // Stwhile = kwdwhile PPexpr BBlock
    public static void rule93(PstInnerNode node){
        hoistKid(0,node);
    }

    // Stprint = kprint PPexprs
    public static void rule94(PstInnerNode node){
        hoistKid(0, node);
    }

    // Strtn = kwdreturn StrtnT
    public static void rule95(PstInnerNode node) {

    }

    // StrtnT = Expr
    public static void rule96(PstInnerNode node) {

    }

    // StrtnT = eps
    public static void rule97(PstInnerNode node) {

    }

    // PPexpr = parens1 Expr parens2
    public static void rule98(PstInnerNode node) {

    }

    // Expr = Oprel Rterm Expr
    public static void rule99(PstInnerNode node) {

    }

    // Expr = Rterm Expr
    public static void rule100(PstInnerNode node) {

    }

    // Expr = eps
    public static void rule101(PstInnerNode node) {

    }

    // Rterm = Opadd Term Rterm
    public static void rule102(PstInnerNode node) {

    }

    // Rterm = Term Rterm
    public static void rule103(PstInnerNode node) {

    }

    // Rterm = eps
    public static void rule104(PstInnerNode node) {

    }

    // Term = Opmul Factcheck Term
    public static void rule105(PstInnerNode node) {

    }

    // Term = Fact Term
    public static void rule106(PstInnerNode node) {

    }

    // Term = eps
    public static void rule107(PstInnerNode node) {

    }

    // Factcheck = Fact
    public static void rule108(PstInnerNode node) {

    }

    // Factcheck = Deref_id
    public static void rule109(PstInnerNode node) {

    }

    // Fact = Basekind
    public static void rule110(PstInnerNode node) {

    }

    // Fact = Addrof_id
    public static void rule111(PstInnerNode node) {

    }

    // Fact = PPexpr
    public static void rule112(PstInnerNode node) {

    }

    // Fact = id FactT
    public static void rule113(PstInnerNode node) {

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
