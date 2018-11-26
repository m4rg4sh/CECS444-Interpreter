import Exceptions.*;
import PST.*;
import Symbols.*;
import Tokens.Token;
import java.io.IOException;
import java.util.*;


/**
 * This class represents the machine portion of the LL parse machine. It is responsible for setting up the prediction
 * table, prediction stack, and token stream.
 *
 * @author Kevin Bui <Kevinthuybui@gmail.com>
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
    
    /**
     * Default constructor. Initializes the tokenStream and stack.
     */
    private LLParseMachine() {
        tokenStream = new TokenStreamReader(System.in);
        initializeStack();
    }
    
    /**
     * Initializes stack and adds the EOF and PGM Nodes.
     */
    private void initializeStack() {
        stack = new Stack<>();
        stack.push(new PstInnerNode(Terminal.EOF, 0));
        stack.push(new PstInnerNode(START_SYMBOL, 1));
        
        pstRoot = stack.peek();
    }
    
    /**
     * This handles setting up the parseTable, reading from the token stream, getting the top of stack and handling
     * the algorithm for checking the syntaax and building the PST and AST.
     * @throws ParserException
     */
    private void parse() throws ParserException{
        parseTable = ParseTableGenerator.getParseTable();
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
                    //add token to tree
                    pstPointer.setToken(token);
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
        printPSTandAST();
    }
    
    /**
     * This function handles printing the PST and converting and printing the AST.
     */
    private void printPSTandAST() {
        System.out.println("PST:");
        serializeTree(pstRoot);
        pst2ast(pstRoot);
        System.out.println("AST:");
        serializeTree(pstRoot);
    }
    
    /**
     * This function handles the process of looking up a <code>Prediction</code> and adding the <code>Symbols</code>
     * to the stack.
     * @param topOfStack The current top of stack
     * @param token The token from input
     * @param parentNode The node whose children are to be filled
     * @throws ParserException
     */
    private void executeRule(Symbol topOfStack, Token token, PstInnerNode parentNode) throws ParserException {
        Prediction prediction = new Prediction((NonTerminal) topOfStack, Terminal.valueOf(token.getId()));
        if (parseTable.containsKey(prediction)) {
            Rule rule = parseTable.get(prediction);
            parentNode.setRule(rule.getId());
            List<Symbol> rhs = new ArrayList<>(rule.getRhs());
            Collections.reverse(rhs);
            List<PstNode> newNodes = new ArrayList<>();
            for (Symbol symbol : rhs) {
                PstNode newNode;
                if (symbol instanceof Terminal) {
                    newNode = new PstLeafNode(symbol);
                } else {
                    newNode = new PstInnerNode(symbol);
                }
                stack.push(newNode);
                newNodes.add(newNode);
            }
            Collections.reverse(newNodes);
            for (PstNode node : newNodes) {
                parentNode.addChild(node);
            }
        } else {
            throw new ParserException("ParseException: Prediction not in table. Token:" + token +", Symbol:" +
            topOfStack);
        }
    }
    
    /**
     * This function recursively prints the node and its children.
     * @param node The node to be printed
     */
    private void serializeTree(PstNode node) {
        serializeTree(node, 0);
        System.out.printf("%n");
    }
    
    /**
     * This function prints the node with given indent level.
     * @param node The node to be printed
     * @param identation The current level of indent
     */
    private void serializeTree(PstNode node, int identation){
        System.out.printf("%n");
        for (int i = 0; i < identation; ++i) {
            System.out.printf("\t");
        }
        System.out.printf("(Node: ");
        printLocalFieldInfo(node);
        if (node instanceof PstInnerNode) {
            for (PstNode child : ((PstInnerNode) node).getChildren()) {
                serializeTree(child, identation+1);
            }
        }
        System.out.printf("%n");
        for (int i = 0; i < identation; ++i) {
            System.out.printf("\t");
        }
        System.out.printf(")");
    }
    
    /**
     * Prints the local field info of given node.
     * @param node the node to be printed
     */
    private void printLocalFieldInfo(PstNode node) {
        System.out.printf("\t(TYPE=%s; SYMBOL=%s; ID=%d", node.getSymbol().getClass().getSimpleName(), node.getSymbol(), node.hashCode()); //TODO implement hascode in a useful way
        Token token = node.getToken();
        if (null != token) {
            System.out.printf("; %s", token.toTreeString());
        }
        System.out.printf(")");

    }
    
    /**
     * This function converts given PST node to AST node
     * @param node The node to be converted
     */
    private void pst2ast (PstNode node) {
        if (node instanceof PstInnerNode) {
            for (PstNode kid : ((PstInnerNode) node).getChildren()) {
                pst2ast(kid);
            }
            NodeConverter.convertNode((PstInnerNode) node);
        }
        //no else needed, leaf nodes get converted while the parent node is handled
    }

}
