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
 * This class creates tokens using the output from our lexer. The user must stop reading tokens when encountering
 * the EOF token or a IOError will be raised.
 */

public class TokenStreamReader extends InputStreamReader {
    /**
     * This regex Pattern will match our token output and groups the values.
     */
    
    private static final Pattern statePattern =
            Pattern.compile("\\(Tok: *(\\d*) line= *(\\d*) str= \"(.*)\"(?:\\)|(?: int= *\\d*\\)| float= *\\d*\\.\\d*\\))?)");
    
    /**
     * This Matcher uses our Pattern to find the values. We initialize it with an empty string to reuse later.
     */
    
    private Matcher tokenMatcher = statePattern.matcher("");
    
    /**
     * This Token is a buffer to store the result of peek.
     */
    private Token tokenBuffer;
    
    /**
     * Default constructor
     * @param in The InputStream we are wrapping.
     */
    TokenStreamReader(InputStream in) {
        super(in);
    }
    
    /**
     * This function reads a line from stdin and creates a <code>Token</code>.
     * @return Token the output token.
     * @throws TokenInputMalformedException If the input doesn't match our <code>Pattern</code> this error is thrown.
     * @throws IOException Unhandled exception thrown by <code>getNextLine</code>.
     */
    
    public Token getNextToken() throws TokenInputMalformedException, IOException {
        if(tokenBuffer == null) {
            StringBuilder nextTokenStringBuilder = new StringBuilder();
            getNextLine(nextTokenStringBuilder);
            String tokenString = nextTokenStringBuilder.toString();
            return createToken(tokenString);
        }
        else{
            Token result = tokenBuffer;
            tokenBuffer = null;
            return result;
        }
    }
    public Token peek() throws TokenInputMalformedException, IOException {
        tokenBuffer = getNextToken();
        return tokenBuffer;
    }
    
    /**
     * This function uses <code>tokenMatcher</code> to validate token input and get the value of the three capture
     * groups. These values are passed onto <code>TokenFactory</code> to create the necessary Token.
     * @param tokenString The string input
     * @return Token
     * @throws TokenInputMalformedException If <code>tokenMatcher</code> is unable to find a match this error is thrown.
     */
    
    private Token createToken(String tokenString) throws TokenInputMalformedException {
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

    /**
     * Reads until the end of line adding characters to the given <code>StringBuilder</code>. This code ignores the EOF character.
     * @param nextTokenStringBuilder The StringBuilder used to aggregate char from the <code>InputStream</code>.
     * @throws IOException Unhandled exception from <code>read</code>.
     */
    
    private void getNextLine(StringBuilder nextTokenStringBuilder) throws IOException {
        char nextChar = (char) read();
        while(nextChar != '\n'){
            nextTokenStringBuilder.append(nextChar);
            
//            int nextInt = read();
//            if (nextInt == -1) break;
//            else nextChar = (char) nextInt;
            nextChar = (char) read();
            if(nextChar == '~') break;
        }
    }
}
