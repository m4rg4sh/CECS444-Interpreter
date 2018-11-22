import Exceptions.*;
import Tree.PST.*;
import Symbols.*;
import Tokens.Token;
import Tree.TreeNode;

import java.io.IOException;
import java.util.*;


/**
 * This class represents the machine portion of the LL parse machine. It is responsible for setting up the prediction
 * table, prediction stack, and token stream.
 */
public class LLParseMachine {
    private static final Symbol START_SYMBOL = NonTerminal.PGM;
    private static  ParseTable parseTable;
    private Stack<PstNode> stack;
    private TokenStreamReader tokenStream;
    private PstNode pstRoot;
    
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
        stack.push(new PstInnerNode(Terminal.EOF, 0));
        stack.push(new PstInnerNode(START_SYMBOL, 1));
        
        pstRoot = stack.peek();
    }

    private void parse() throws ParserException{
        parseTable = PredictionTableGenerator.createParseTable();
        boolean parsing = true;
        while(parsing) {
            try {
                Token token = tokenStream.peek();
                PstNode pstPointer = stack.pop();
                Symbol topOfStack = pstPointer.getSymbol();
                if (topOfStack instanceof Terminal && token.getId() == topOfStack.getId()) {
                    if (token.getId() == Terminal.EOF.getId()) {
                        parsing = false;
                    }
                    tokenStream.getNextToken(); //Advances the input. Stack already popped.
                } else if (topOfStack instanceof Terminal) {
                    throw new ParserException("ParseException: Top of stack is Terminal. Token :" + token + ", Symbol :"
                     + topOfStack);
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
    
        serializeTree(pstRoot);
    }

    private void executeRule(Symbol topOfStack, Token token, PstInnerNode parentNode) throws ParserException {
        Prediction prediction = new Prediction((NonTerminal) topOfStack, Terminal.valueOf(token.getId()));
        if (parseTable.containsKey(prediction)) {
            Rule rule = (Rule) parseTable.get(prediction);
            List<Symbol> rhs = new ArrayList<>(rule.getRhs());
            Collections.reverse(rhs);
            for (Symbol symbol : rhs) {
                PstNode newNode;
                if (symbol instanceof Terminal) {
                    newNode = new PstLeafNode(symbol, token);
                } else {
                    newNode = new PstInnerNode(symbol, rule.getId());
                }
                stack.push(newNode);
                parentNode.addChild(newNode);
            }
        } else {
            throw new ParserException("ParseException: Prediction not in table. Token:" + token +", Symbol:" +
            topOfStack);
        }
    }
    
    private void serializeTree(TreeNode node){
        System.out.printf("%n(Node: ");
        printLocalFieldInfo(node);
        if (node instanceof PstInnerNode) {
            for (PstNode child : ((PstInnerNode) node).getChildren()) {
                serializeTree(child);
            }
        }
        System.out.println(")");
    }

    private void printLocalFieldInfo(TreeNode node) {
        System.out.printf("\t(Type:%s; ID:%d)", node.getSymbol().getClass().getSimpleName(), node.hashCode()); //TODO implement hascode in a useful way
        //TODO print the rest of the stuff, I don't get what he wants here

    }
}
