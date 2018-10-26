import Exceptions.TokenInputMalformedException;
import Symbols.Terminal;
import Tokens.Token;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
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
            Terminal terminal = getTerminal(Integer.parseInt(tokenMatcher.group(1)));
            int lineNumber = Integer.parseInt(tokenMatcher.group(2));
            String tokenValue = tokenMatcher.group(3);
            return TokenFactory.createToken(terminal, lineNumber, tokenValue);
        } else {
            throw new TokenInputMalformedException();
        }
    }
    
    private Terminal getTerminal(int terminalId) {
        Terminal terminal = Terminal.valueOf(terminalId);
        if (terminal.getId() != terminalId) {
            throw new InputMismatchException("Unknown TokenID");
        }
        return terminal;
    }
}
