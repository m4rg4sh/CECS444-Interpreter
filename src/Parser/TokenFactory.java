package Parser;

import Symbols.Terminal;
import Tokens.*;

/**
 * This class holds the necessary methods to create token objects
 *
 * @author Kevin Bui <Kevinthuybui@gmail.com>
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class TokenFactory {

    private TokenFactory() {
        //hides default constructor
    }

    public static Token createToken(Terminal terminal, int lineNumber, String tokenValue) {
        switch (terminal) {
            case INT:
                return new IntToken(terminal, lineNumber, tokenValue, Integer.parseInt(tokenValue));
            case FLOAT:
                return new FloatToken(terminal, lineNumber, tokenValue, (double) Float.parseFloat(tokenValue));
            default:
                return new Token(terminal, lineNumber, tokenValue);
        }
    }
}