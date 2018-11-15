import Exceptions.*;
import PST.*;
import Symbols.*;
import Tokens.Token;

import java.io.IOException;
import java.util.*;


/**
 * This class represents the machine portion of the LL parse machine. It is responsible for setting up the prediction
 * table, prediction stack, and token stream.
 */
public class LLParseMachine {
    private static final Symbol START_SYMBOL = NonTerminal.PGM;
    private static final Map<Prediction, ArrayList<Symbol>> predictionTable = PredictionTableGenerator.createPredictionTable();
    private Stack<PstNode> stack;
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
        tokenStream = new TokenStreamReader(System.in);
        initializeStack();
    }

    private void initializeStack() {
        stack = new Stack<>();
        stack.push(new PstInnerNode(Terminal.EOF));
        stack.push(new PstInnerNode(START_SYMBOL));
    }

    private void parse() throws ParserException{
        boolean parsing = true;
        while(parsing) {
            try {
                Token token = tokenStream.peek();
                PstNode pstPointer = stack.pop();
                Symbol topOfStack = pstPointer.getSymbol();
                if (token.getId() == topOfStack.getId()) {
                    if (token.getId() == Terminal.EOF.getId()) {
                        parsing = false;
                    }
                    tokenStream.getNextToken(); //Advances the input. Stack already popped.
                } else if (topOfStack instanceof Terminal) {
                    throw new ParserException("ParseException");
                } else {
                    if (pstPointer instanceof PstInnerNode) {
                        executeRule(topOfStack, token, (PstInnerNode) pstPointer);
                    } else {
                        throw new ParserException("InnerNode expected, but LeafNode found");
                    }
                }
            } catch (IOException | TokenInputMalformedException e) {
                throw new ParserException("Tokens Error");
            }
        }
    }

    private void executeRule(Symbol topOfStack, Token token, PstInnerNode parentNode) throws ParserException {
        Prediction prediction = new Prediction((NonTerminal) topOfStack, Terminal.valueOf(token.getId()));
        if (predictionTable.containsKey(prediction)) {
            ArrayList<Symbol> rhsOfRule = predictionTable.get(prediction);
            Collections.reverse(rhsOfRule);
            for (Symbol symbol : rhsOfRule) {
                PstNode newNode;
                if (symbol instanceof Terminal) {
                    newNode = new PstLeafNode(symbol, token);
                } else {
                    newNode = new PstInnerNode(symbol);
                }
                stack.push(newNode);
                parentNode.addChild(newNode);
            }
        } else {
            throw new ParserException("ParseException");
        }
    }
    
    private void printParseTree(PstNode node, int level){
        if (node == null) return;
        
        System.out.println("(Node: ");
        System.out.printf("\t(Type:%s; ID:%d)%n", node.getSymbol().getClass().getSimpleName(), 1);
        if (node instanceof  PstInnerNode){
            for (PstNode child : ((PstInnerNode) node).getChildren()){
                printParseTree(child, level + 1);
            }
        }
        System.out.println(")");
    
    }
}
