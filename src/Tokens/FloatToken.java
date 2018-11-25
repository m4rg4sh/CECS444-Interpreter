package Tokens;

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

    public FloatToken(int id, int lineNumber, String name, double value) {
        super(id,lineNumber, name);
        this.value = value;
    }
    @Override
    public String toString()
    {
        return String.format( "(Tok: %2d line= %2d str= \"%s\" float= %.2f)", getId(),
                getLineNumber(), getCodeString(), value);
    }
}
