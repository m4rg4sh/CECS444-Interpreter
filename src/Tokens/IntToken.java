package Tokens;

import Symbols.Symbol;

/**
 * Subclass of Token which represents an Integer.
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */

public class IntToken extends Token {
    /**
     * Holds the actual int value of the token
     */
    private int value;

    public IntToken(Symbol symbol, int lineNumber, String name, int value) {
        super(symbol, lineNumber, name);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Converts the token to a string which is formatted correctly for PST/AST output
     * @return formatted string representation of the token
     */
    public String toTreeString() {
        return String.format("CODE=\"%s\"; INT=%d LINE=%d", getCodeString(), value, getLineNumber());
    }

    /**
     * Converts the Token to a String according to the specifications of the project
     * @return The token as a string
     */
    @Override
    public String toString()
    {
        return String.format( "(Tok: %2d line= %2d str= \"%s\" int= %d)", getId(),
            getLineNumber(), getCodeString(), value);
    }
}
