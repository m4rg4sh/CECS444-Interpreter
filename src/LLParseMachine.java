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
            case 4:
                P2aRules.rule4(node);
                break;
            case 5:
                P2aRules.rule5(node);
                break;
            case 6:
                P2aRules.rule6(node);
                break;
            case 7:
                P2aRules.rule7(node);
                break;
            case 8:
                P2aRules.rule8(node);
                break;
            case 9:
                P2aRules.rule9(node);
                break;
            case 10:
                P2aRules.rule10(node);
                break;
            case 11:
                P2aRules.rule11(node);
                break;
            case 12:
                P2aRules.rule12(node);
                break;
            case 13:
                P2aRules.rule13(node);
                break;
            case 14:
                P2aRules.rule14(node);
                break;
            case 15:
                P2aRules.rule15(node);
                break;
            case 16:
                P2aRules.rule16(node);
                break;
            case 17:
                P2aRules.rule17(node);
                break;
            case 18:
                P2aRules.rule18(node);
                break;
            case 19:
                P2aRules.rule19(node);
                break;
            case 20:
                P2aRules.rule20(node);
                break;
            case 21:
                P2aRules.rule21(node);
                break;
            case 22:
                P2aRules.rule22(node);
                break;
            case 23:
                P2aRules.rule23(node);
                break;
            case 24:
                P2aRules.rule24(node);
                break;
            case 25:
                P2aRules.rule25(node);
                break;
            case 26:
                P2aRules.rule26(node);
                break;
            case 27:
                P2aRules.rule27(node);
                break;
            case 28:
                P2aRules.rule28(node);
                break;
            case 29:
                P2aRules.rule29(node);
                break;
            case 30:
                P2aRules.rule30(node);
                break;
            case 31:
                P2aRules.rule31(node);
                break;
            case 32:
                P2aRules.rule32(node);
                break;
            case 33:
                P2aRules.rule33(node);
                break;
            case 34:
                P2aRules.rule34(node);
                break;
            case 35:
                P2aRules.rule35(node);
                break;
            case 36:
                P2aRules.rule36(node);
                break;
            case 37:
                P2aRules.rule37(node);
                break;
            case 38:
                P2aRules.rule38(node);
                break;
            case 39:
                P2aRules.rule39(node);
                break;
            case 40:
                P2aRules.rule40(node);
                break;
            case 41:
                P2aRules.rule41(node);
                break;
            case 42:
                P2aRules.rule42(node);
                break;
            case 43:
                P2aRules.rule43(node);
                break;
            case 44:
                P2aRules.rule44(node);
                break;
            case 45:
                P2aRules.rule45(node);
                break;
            case 46:
                P2aRules.rule46(node);
                break;
            case 47:
                P2aRules.rule47(node);
                break;
            case 48:
                P2aRules.rule48(node);
                break;
            case 49:
                P2aRules.rule49(node);
                break;
            case 50:
                P2aRules.rule50(node);
                break;
            case 51:
                P2aRules.rule51(node);
                break;
            case 52:
                P2aRules.rule52(node);
                break;
            case 53:
                P2aRules.rule53(node);
                break;
            case 54:
                P2aRules.rule54(node);
                break;
            case 55:
                P2aRules.rule55(node);
                break;
            case 56:
                P2aRules.rule56(node);
                break;
            case 57:
                P2aRules.rule57(node);
                break;
            case 58:
                P2aRules.rule58(node);
                break;
            case 59:
                P2aRules.rule59(node);
                break;
            case 60:
                P2aRules.rule60(node);
                break;
            case 61:
                P2aRules.rule61(node);
                break;
            case 62:
                P2aRules.rule62(node);
                break;
            case 63:
                P2aRules.rule63(node);
                break;
            case 64:
                P2aRules.rule64(node);
                break;
            case 65:
                P2aRules.rule65(node);
                break;
            case 66:
                P2aRules.rule66(node);
                break;
            case 67:
                P2aRules.rule67(node);
                break;
            case 68:
                P2aRules.rule68(node);
                break;
            case 69:
                P2aRules.rule69(node);
                break;
            case 70:
                P2aRules.rule70(node);
                break;
            case 71:
                P2aRules.rule71(node);
                break;
            case 72:
                P2aRules.rule72(node);
                break;
            case 73:
                P2aRules.rule73(node);
                break;
            case 74:
                P2aRules.rule74(node);
                break;
            case 75:
                P2aRules.rule75(node);
                break;
            case 76:
                P2aRules.rule76(node);
                break;
            case 77:
                P2aRules.rule77(node);
                break;
            case 78:
                P2aRules.rule78(node);
                break;
            case 79:
                P2aRules.rule79(node);
                break;
            case 80:
                P2aRules.rule80(node);
                break;
            case 81:
                P2aRules.rule81(node);
                break;
            case 82:
                P2aRules.rule82(node);
                break;
            case 83:
                P2aRules.rule83(node);
                break;
            case 84:
                P2aRules.rule84(node);
                break;
            case 85:
                P2aRules.rule85(node);
                break;
            case 86:
                P2aRules.rule86(node);
                break;
            case 87:
                P2aRules.rule87(node);
                break;
            case 88:
                P2aRules.rule88(node);
                break;
            case 89:
                P2aRules.rule89(node);
                break;
            case 90:
                P2aRules.rule90(node);
                break;
            case 91:
                P2aRules.rule91(node);
                break;
            case 92:
                P2aRules.rule92(node);
                break;
            case 93:
                P2aRules.rule93(node);
                break;
            case 94:
                P2aRules.rule94(node);
                break;
            case 95:
                P2aRules.rule95(node);
                break;
            case 96:
                P2aRules.rule96(node);
                break;
            case 97:
                P2aRules.rule97(node);
                break;
            case 98:
                P2aRules.rule98(node);
                break;
            case 99:
                P2aRules.rule99(node);
                break;
            case 100:
                P2aRules.rule100(node);
                break;
            case 101:
                P2aRules.rule101(node);
                break;
            case 102:
                P2aRules.rule102(node);
                break;
            case 103:
                P2aRules.rule103(node);
                break;
            case 104:
                P2aRules.rule104(node);
                break;
            case 105:
                P2aRules.rule105(node);
                break;
            case 106:
                P2aRules.rule106(node);
                break;
            case 107:
                P2aRules.rule107(node);
                break;
            case 108:
                P2aRules.rule108(node);
                break;
            case 109:
                P2aRules.rule109(node);
                break;
            case 110:
                P2aRules.rule110(node);
                break;
            case 111:
                P2aRules.rule111(node);
                break;
            case 112:
                P2aRules.rule112(node);
                break;
            case 113:
                P2aRules.rule113(node);
                break;
            case 114:
                P2aRules.rule114(node);
                break;
            case 115:
                P2aRules.rule115(node);
                break;
            case 116:
                P2aRules.rule116(node);
                break;
            case 117:
                P2aRules.rule117(node);
                break;
            case 118:
                P2aRules.rule118(node);
                break;
            case 119:
                P2aRules.rule119(node);
                break;
            case 120:
                P2aRules.rule120(node);
                break;
            case 121:
                P2aRules.rule121(node);
                break;
            case 122:
                P2aRules.rule122(node);
                break;
            case 123:
                P2aRules.rule123(node);
                break;
            case 124:
                P2aRules.rule124(node);
                break;
            case 125:
                P2aRules.rule125(node);
                break;
            case 126:
                P2aRules.rule126(node);
                break;
            case 127:
                P2aRules.rule127(node);
                break;
            case 128:
                P2aRules.rule128(node);
                break;
            case 129:
                P2aRules.rule129(node);
                break;
            case 130:
                P2aRules.rule130(node);
                break;
            case 131:
                P2aRules.rule131(node);
            default:
                //TODO throw error
        }

    }
}
