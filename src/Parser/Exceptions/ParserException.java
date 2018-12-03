package Parser.Exceptions;

/**
 * This is an exception for the 3 possible parser errors
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class ParserException extends Exception {
    public ParserException() {
        super();
    }

    public ParserException(String e) {
        super(e);
    }
}
