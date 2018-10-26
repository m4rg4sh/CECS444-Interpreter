import Symbols.Terminal;
import Tokens.*;

public class TokenFactory {

    private TokenFactory() {
        //hides default constructor
    }

    public static Token createToken(Terminal terminal, int lineNumber, String tokenValue) {
        switch (terminal) {
            case INT:
                return new IntToken(terminal.getId(), lineNumber, tokenValue, Integer.parseInt(tokenValue));
            case FLOAT:
                return new FloatToken(terminal.getId(), lineNumber, tokenValue, (double) Float.parseFloat(tokenValue));
            case ID:
                return handleID(lineNumber, tokenValue);
            case COMMENT:
                //fallthrough
            case OPARROW:
                //fallthrough
            case OPEQ:
                //fallthrough
            case OPGE:
                //fallthrough
            case OPLE:
                //fallthrough
            case OPNE:
                //fallthrough
            case OPSHL:
                //fallthrough
            case OPSHR:
                //fallthrough
            case DOT:
                //fallthrough
            case MINUS:
                //fallthrough
            case ASTER:
                //fallthrough
            case CARET:
                //fallthrough
            case COLON:
                //fallthrough
            case PLUS:
                //fallthrough
            case SLASH:
                //fallthrough
            case EQUAL:
                //fallthrough
            case ANGLE1:
                //fallthrough
            case ANGLE2:
                //fallthrough
            case BRACE1:
                //fallthrough
            case BRACE2:
                //fallthrough
            case PARENS1:
                //fallthrough
            case PARENS2:
                //fallthrough
            case BRACKET1:
                //fallthrough
            case BRACKET2:
                //fallthrough
            case COMMA:
                //fallthrough
            case SEMI:
                //fallthrough
            case EOF:
                //fallthrough
            case STRING:
                return new Token(terminal.getId(), lineNumber, tokenValue);
            default:
                return new Token(Terminal.ERROR.getId(), lineNumber, tokenValue);
        }
    }

    /**
     * Checks a token of type ID if it matches one of the reserved keywords.
     * If so, it changes the type of the token to the respective keyword and creates the new token.
     * Otherwise it creates a regular token of type ID
     *
     * @param lineNumber the line number of the token
     * @param tokenValue the string which represents the token
     * @return the newly created token object
     */
    private static Token handleID(int lineNumber, String tokenValue) {
        switch (tokenValue) {
            case "prog":
                return new Token(Terminal.KPROG.getId(), lineNumber, tokenValue);
            case "main":
                return new Token(Terminal.KMAIN.getId(), lineNumber, tokenValue);
            case "fcn":
                return new Token(Terminal.KFCN.getId(), lineNumber, tokenValue);
            case "class":
                return new Token(Terminal.KCLASS.getId(), lineNumber, tokenValue);
            case "float":
                return new Token(Terminal.KFLOAT.getId(), lineNumber, tokenValue);
            case "int":
                return new Token(Terminal.KINT.getId(), lineNumber, tokenValue);
            case "string":
                return new Token(Terminal.KSTRING.getId(), lineNumber, tokenValue);
            case "if":
                return new Token(Terminal.KIF.getId(), lineNumber, tokenValue);
            case "elseif":
                return new Token(Terminal.KELSEIF.getId(), lineNumber, tokenValue);
            case "else":
                return new Token(Terminal.KELSE.getId(), lineNumber, tokenValue);
            case "while":
                return new Token(Terminal.KWHILE.getId(), lineNumber, tokenValue);
            case "input":
                return new Token(Terminal.KINPUT.getId(), lineNumber, tokenValue);
            case "print":
                return new Token(Terminal.KPRINT.getId(), lineNumber, tokenValue);
            case "new":
                return new Token(Terminal.KNEW.getId(), lineNumber, tokenValue);
            case "return":
                return new Token(Terminal.KRETURN.getId(), lineNumber, tokenValue);
            case "var":
                return new Token(Terminal.KVAR.getId(), lineNumber, tokenValue);
            default:
                return new Token(Terminal.ID.getId(), lineNumber, tokenValue);
        }
    }
}