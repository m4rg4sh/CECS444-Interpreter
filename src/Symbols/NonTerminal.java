package Symbols;

/**
 * This is an enumeration of all possible Non-Terminal symbols.
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 * @author Kevin Bui<Kevinthuybui@gmail.com>
 * @author Gabe Flores <rgabeflores@gmail.com>
 */
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
    STASGN(42),
    LVAL(43),
    AREF(44),
    KKEXPR(45),
    FCALL(46),
    PPEXPRS(47),
    STIF(48),
    ELSEPART(49),
    STWHILE(50),
    STPRINT(51),
    STRTN(52),
    PPEXPR(53),
    EXPR(54),
    RTERM(55),
    TERM(56),
    FACT(57),
    ADDROF_ID(58),
    OPREL(59),
    LTHAN(60),
    GTHAN(61),
    OPADD(62),
    OPMUL(63),
    VARITEMT(64),
    VARID(65),
    VARSPECT(66),
    BBEXPRST(67),
    CLASSDECLT(68),
    PPARMLISTT(69),
    FACTT(70),
    FACTCHECK(71),
    STMTT(72),
    LVALT(73),
    PPEXPRST(74),
    STRTNT(75);

    private int id;

    public int getId() {
        return id;
    }

    NonTerminal(int id) {
        this.id = id;
    }

    @Override
    public boolean createsScope() {
        return false;
    }
}
