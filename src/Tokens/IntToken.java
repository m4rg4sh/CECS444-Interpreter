package Tokens;

/**
 * Subclass of Token which represents an Integer.
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */

public class IntToken extends Token {
    /**
     * Holds the actual int value of the token
     */
    private int value;

    public IntToken(int id, int lineNumber, String name, int value) {
        super(id, lineNumber, name);
        this.value = value;
    }
    @Override
    public String toString()
    {
        return String.format( "(Tok: %2d line= %2d str= \"%s\" int= %d)", getId(),
            getLineNumber(), getCodeString(), value);
    }
}
