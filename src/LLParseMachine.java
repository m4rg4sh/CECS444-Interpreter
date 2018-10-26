import Symbols.*;
import Token.Token;

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
            Token token = tokenStream.getNextToken();
            Symbol topOfStack = stack.pop();
            if (token.getId() == topOfStack.getId()) {
                if (token.getId() == Terminal.EOF.getId()) {
                    parsing = false;
                }
            } else if (topOfStack instanceof Terminal){
                throw new ParserException("Parse Exception");
            }  else {
                executeMatchingRule(topOfStack, token);
            }
        }
    }

    private void executeMatchingRule(Symbol topOfStack, Token token) throws ParserException {
        Prediction prediction = new Prediction((NonTerminal) topOfStack, Terminal.valueOf(token.getId()));
        if (predictionTable.containsKey(prediction)) {
            ArrayList<Symbol> rule = predictionTable.get(prediction);
            Collections.reverse(rule);
            for (Symbol s : rule) {
                stack.push(s);
            }
        } else {
            throw new ParserException("Empty cell in ParseTable");
        }
    }
}
