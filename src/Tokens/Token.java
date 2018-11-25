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

    public int getId() {
        return id;
    }
    
    public int getLineNumber()
    {
        return lineNumber;
    }
    
    public String getCodeString() {
    
        return codeString;
    }

    /**
     * Converts the Token to a String according to the specifications of the project
     * @return The token as a string
     */
    @Override
    public String toString() {
        return String.format( "(Tok: %2d line= %2d str= \"%s\")", getId(), getLineNumber(), getCodeString()); }
}
