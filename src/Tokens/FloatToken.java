package Tokens;

import Symbols.Symbol;

/**
 * Subclass of Token which represents a Float.
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class FloatToken extends Token {
    /**
     * Holds the actual float value of the Token
     * Using double to increase precision
     */
    private double value;

    public FloatToken(Symbol symbol, int lineNumber, String name, double value) {
        super(symbol,lineNumber, name);
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    /**
     * Converts the token to a string which is formatted correctly for PST/AST output
     * @return formatted string representation of the token
     */
    public String toTreeString() {
        return String.format("CODE=\"%s\"; FLOAT=%.2f LINE=%d", getCodeString(), value, getLineNumber());
    }

    /**
     * Converts the Token to a String according to the specifications of the project
     * @return The token as a string
     */
    @Override
    public String toString()
    {
        return String.format( "(Tok: %2d line= %2d str= \"%s\" float= %.2f)", getId(),
                getLineNumber(), getCodeString(), value);
    }
}
