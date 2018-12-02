package Symbols;

import java.util.Arrays;

/**
 * This represents the possible terminals in the language.
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 * @author Kevin Bui <Kevinthuybui@gmail.com>
 * @author Gabe Flores <rgabeflores@gmail.com>
 */
public enum Terminal implements Symbol {
    COMMENT(1),
    ID(2),
    INT(3),
    FLOAT(4),
    STRING(5),
    COMMA(6),
    SEMI(7),
    KPROG(10,true),
    KMAIN(11,true),
    KFCN(12,true),
    KCLASS(13,true),
    KFLOAT(15),
    KINT(16),
    KSTRING(17),
    KIF(18),
    KELSEIF(19),
    KELSE(20),
    KWHILE(21),
    KINPUT(22),
    KPRINT(23),
    KNEW(24),
    KRETURN(25),
    KVAR(26),
    ANGLE1(31),
    ANGLE2(32),
    BRACE1(33),
    BRACE2(34),
    BRACKET1(35),
    BRACKET2(36),
    PARENS1(37),
    PARENS2(38),
    ASTER(41),
    CARET(42),
    COLON(43),
    DOT(44),
    EQUAL(45),
    MINUS(46),
    PLUS(47),
    SLASH(48),
    OPARROW(51),
    OPEQ(52),
    OPNE(53),
    OPLE(54),
    OPGE(55),
    OPSHL(56),
    OPSHR(57),
    EOF(0),
    ERROR(99),
    EPSILON(100),
    AMPERSAND(101);

    private final int id;
    private final boolean createsScrope;

    Terminal(int id, boolean createsScope) {
        this.id = id;
        this.createsScrope = createsScope;
    }

    Terminal(int id) {
        this(id,false);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean createsScope() {
        return createsScrope;
    }

    /**
     * This function returns the matching Terminal for given id value.
     * @param value The id of the desired Terminal
     * @return The matching terminal
     */
    public static Terminal valueOf(int value) {
        return Arrays.stream(values())
                .filter(terminal -> terminal.id == value)
                .findFirst().orElse(ERROR);
    }
}
