package Symbols;

public enum NonTerminal implements Symbol {
    PGM(0),
    MAIN(1),
    BBLOCK(2),
    VARGROUP(3),
    PPVARLIST(4),
    VARLIST(5),
    VARITEM(6),
    VARDECL(7),
    SIMPLEKIND(8),
    BASEKIND(9),
    CLASSID(10),
    VARSPEC(11),
    ARRSPEC(12),
    KKINT(13),
    DEREF_ID(14),
    DEREF(15),
    VARINIT(16),
    BBEXPRS(17),
    EXPRLIST(18),
    MOREEXPRS(19),
    CLASSDECL(20),
    BBCLASSITEMS(21),
    CLASSHEADER(22),
    CLASSMOM(23),
    CLASSITEMS(24),
    CLASSGROUP(25),
    CLASS_CTRL(26),
    INTERFACES(27),
    MDDECLS(28),
    MDHEADER(29),
    MD_ID(30),
    FCNDEFS(31),
    FCNDEF(32),
    FCNHEADER(33),
    FCNID(34),
    RETKIND(35),
    PPARMLIST(36),
    VARSPECS(37),
    MORE_VARSPECS(38),
    PPONLY(39),
    STMTS(40),
    STMT(41),
    STMT(42),
    STASGN(43),
    LVAL(44),
    AREF(45),
    KKEXPR(46),
    FCALL(47),
    PPEXPRS(48),
    STIF(49),
    ELSEPART(50),
    STWHILE(51),
    STPRINT(52),
    STRTN(53),
    PPEXPR(54),
    EXPR(55),
    RTERM(56),
    TERM(57),
    FACT(58),
    ADDROF_ID(59),
    OPREL(60),
    LTHAN(61),
    GTHAN(62),
    OPADD(63),
    OPMUL(64);

    private int id;

    public int getId() {
        return id;
    }

    NonTerminal(int id) {
        this.id = id;
    }
}
