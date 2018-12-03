package Parser.Exceptions;

/**
 * This is the exception for situations where the token regex fails.
 * @author Kevin Bui <Kevinthuybui@gmail.com>
 */
public class TokenInputMalformedException extends Exception {
    public TokenInputMalformedException(String input){
        super(input);
    }

    public TokenInputMalformedException(){
        super();
    }
}
