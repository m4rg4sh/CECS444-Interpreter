import Token.Token;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class creates tokens using the output from our lexer.
 */

public class TokenStreamReader extends InputStreamReader {
    /**
     * This regex Pattern will match our token output and groups the values.
     */
    
    private static final Pattern statePattern =
            Pattern.compile("Tok: *(\\d*) line= *(\\d*) str= \"(.*)\"(( int= *(\\d*))| float= *(\\d*.\\d*))?");
    
    /**
     * This Matcher uses our Pattern to find the values. We initialize it with an empty string to reuse later.
     */
    
    private Matcher tokenMatcher = statePattern.matcher("");
    /**
     * This is the buffer for our peek function.
     */
    int buffer;
    
    /**
     * Default constructor
     * @param in
     */
    TokenStreamReader(InputStream in) {
        super(in);
    }
    
    /**
     *
     * @return
     * @throws TokenInputMalformedException
     * @throws IOException
     */
    
    public Token getNextToken() throws TokenInputMalformedException, IOException {
        StringBuilder nextToken = new StringBuilder();
        
        char nextChar = (char) read();
        while(nextChar != '\n'){
            nextToken.append(nextChar);
            
            int nextInt = read();
            if (nextInt == -1) break;
            else nextChar = (char) nextInt;
        }
        
        String tokenString = nextToken.toString();
        tokenMatcher.reset(tokenString);
        if (tokenMatcher.find()) {
            State state = getState(Integer.parseInt(tokenMatcher.group(1)));
            int lineNumber = Integer.parseInt(tokenMatcher.group(2));
            String tokenValue = tokenMatcher.group(3);
            return TokenFactory.createToken(state, lineNumber, tokenValue);
        } else {
            throw new TokenInputMalformedException();
        }
    }
    
    private State getState(int stateValue) {
        State state;
        Optional<State> potentialState = State.valueOf(stateValue);
        if (potentialState.isPresent()) {
            state = potentialState.get();
        }
        else {
            throw new InputMismatchException();
        }
        switch (state){
            case KPROG:
            case KMAIN:
            case KFCN:
            case KCLASS:
            case KFLOAT:
            case KINT:
            case KSTRING:
            case KIF:
            case KELSEIF:
            case KELSE:
            case KWHILE:
            case KINPUT:
            case KPRINT:
            case KNEW:
            case KRETURN:
            case KVAR:
                return State.ID;
            default:
                break;
        }
        return state;
    }
}
