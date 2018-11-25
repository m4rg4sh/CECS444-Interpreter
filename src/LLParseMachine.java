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
        pst2ast(pstRoot);
        serializeTree(pstRoot);
    }

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
                    newNode = new PstLeafNode(symbol, token);
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

    private void serializeTree(PstNode node) {
        serializeTree(node, 0);
        System.out.printf("%n");
    }

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

    private void printLocalFieldInfo(PstNode node) {
        System.out.printf("\t(Type=%s; Symbol=%s; ID=%d)", node.getSymbol().getClass().getSimpleName(), node.getSymbol(), node.hashCode()); //TODO implement hascode in a useful way
        //TODO print the rest of the stuff, I don't get what he wants here

    }

    private void pst2ast (PstNode node) {
        if (node instanceof PstInnerNode) {
            for (PstNode kid : ((PstInnerNode) node).getChildren()) {
                pst2ast(kid);
            }
            convertNode((PstInnerNode) node);
        }
        //no else needed, leaf nodes get converted while the parent node is handled
    }

    private void convertNode(PstInnerNode node) {
        switch (node.getRuleId()) {
            case 1:
                P2aRules.rule1(node);
                break;
            case 2:
                P2aRules.rule2(node);
                break;
            case 3:
                P2aRules.rule3(node);
                break;
            case 5:
                P2aRules.rule5(node);
                break;
            case 6:
                P2aRules.rule6(node);
                break;
            case 55:
                P2aRules.rule55(node);
                break;
            case 68:
                P2aRules.rule68(node);
                break;
            default:
                //TODO throw error
        }

    }
}
