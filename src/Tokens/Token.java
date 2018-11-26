package Tokens;

/**
 *  This class represents a generic Token
 *
 *  @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 *  @author Kevin Bui <Kevinthuybui@gmail.com>
 */

public class Token {
    /**
     * The original code snippet that the token represents
     */
    private String codeString;

    /**
     * The ID of the token in the A4 language
     */
    private int id;

    /**
     * The line number on which the token was read
     */
    private int lineNumber;

    /**
     * Creates a new token
     * @param id The token ID
     * @param lineNumber The line number on which it was found
     * @param codeString The code snippet that was scanned
     */
    public Token(int id, int lineNumber, String codeString) {
        this.id = id;
        this.codeString = codeString;
        this.lineNumber = lineNumber;
    }

    /**
     * @return the ID of the token
     */
    public int getId() {
        return id;
    }

    /**
     * @return The line number on which the token was found
     */
    public int getLineNumber()
    {
        return lineNumber;
    }

    /**
     * @return The original input code fragment
     */
    public String getCodeString() {
    
        return codeString;
    }

    /**
     * Converts the token to a string which is formatted correctly for PST/AST output
     * @return formatted string representation of the token
     */
    public String toTreeString() {
        return String.format("CODE=\"%s\"; LINE=%d", getCodeString(), getLineNumber());
    }

    /**
     * Converts the Token to a String according to the specifications of the project
     * @return The token as a string
     */
    @Override
    public String toString() {
        return String.format( "(Tok: %2d line= %2d str= \"%s\")", getId(), getLineNumber(), getCodeString()); }
}
