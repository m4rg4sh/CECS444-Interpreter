import Symbols.*;
import Tokens.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;


/**
 * This class represents the machine portion of the LL parse machine. It is responsible for setting up the prediction
 * table, prediction stack, and token stream.
 */
public class LLParseMachine {
    private static final HashMap<Prediction, ArrayList<Symbol>> predictionTable = PredictionTableGenerator.createPredictionTable();
    private Stack<Symbol> stack;
    private TokenStreamReader tokenStream;
    
    public static void main(String[] args){
        try {
            new LLParseMachine().parse();
        }
        catch (ParserException e) {
            System.out.println(e.getMessage());
        }
    }

    private LLParseMachine() {
        stack = new Stack<>();
        stack.push(Terminal.EOF);
        tokenStream = new TokenStreamReader(System.in);
    }

    private void parse() throws ParserException{
        boolean parsing = true;
        while(parsing) {
            try {
                Token token = tokenStream.getNextToken();
                Symbol topOfStack = stack.peek();
                if (token.getId() == topOfStack.getId()) {
                    if (token.getId() == Terminal.EOF.getId()) {
                        parsing = false;
                    } else {
                        stack.pop();
                    }
                } else if (topOfStack instanceof Terminal) {
                    throw new ParserException("ParseException");
                } else {
                    executeRule(topOfStack, token);
                }
            } catch (IOException | TokenInputMalformedException e) {
                throw new ParserException("Tokens Error");
            }
        }
    }

    private void executeRule(Symbol topOfStack, Token token) throws ParserException {
        Prediction prediction = new Prediction((NonTerminal) topOfStack, Terminal.valueOf(token.getId()));
        if (predictionTable.containsKey(prediction)) {
            ArrayList<Symbol> cell = predictionTable.get(prediction);
            stack.pop();
            Collections.reverse(cell);
            for (Symbol s : cell) {
                stack.push(s);
            }
        } else {
            throw new ParserException("ParseException");
        }
    }
}
