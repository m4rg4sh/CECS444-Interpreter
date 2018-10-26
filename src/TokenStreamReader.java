import Token.Token;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;

public class TokenStreamReader extends InputStreamReader {
    TokenStreamReader(InputStream in) {
        super(in);
    }

    //TODO should directly advance the input after returning the token
    public Token getNextToken() throws InputMismatchException{
        State state = getState();
        int lineNumber = getLineNumber();
        String tokenValue = getTokenValue();
        return TokenFactory.createToken(state, lineNumber, tokenValue);
    }
    
    //TODO Implement token parsing
    private String getTokenValue() {
        return null;
    }
    
    private int getLineNumber() {
        return 0;
    }
    
    private State getState() {
        return null;
    }
}
