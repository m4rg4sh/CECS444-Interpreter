package Interpreter;

import Interpreter.Exceptions.InterpreterException;
import Interpreter.Symtab.SymtabEntry;
import Tokens.FloatToken;
import Tokens.IntToken;
import Tree.Ast.InnerNode;
import Tree.Ast.Node;


public class EvaluationFunctions {

    public static Object evaluateCode(Node astNode) {
        switch (astNode.getToken().getId()) { //check which ID to use
            //TODO implement all the handlers
            case 2: //ID
                return code2(astNode);
            case 3: //INT
                return code3(astNode);
            case 4: //FLOAT
                return code4(astNode);
            case 10: //KPROG
                return code10(astNode);
            case 11: //KMAIN
                return code11(astNode);
            case 26: //KVAR
                return code26(astNode);
            case 45: //EQUAL
                return code45(astNode);
            case 47: //PLUS
                return code47(astNode);
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static Object code2(Node astNode) {
        //just return the symtabentry
        return astNode.getSymtabEntry();
    }

    private static Object code3(Node astNode) {
        //just return the value
        IntToken token = (IntToken) astNode.getToken();
        return token.getValue();
    }

    private static Object code4(Node astNode) {
        //just return the value
        FloatToken token = (FloatToken) astNode.getToken();
        return token.getValue();
    }

    private static Object code10(Node astNode) {
        //kprog doesn't do anything, just evaluate the children
        InnerNode node = (InnerNode) astNode;
        for (Node child : node.getChildren()) {
            evaluateCode(child);
        }
        return null;
    }

    private static Object code11(Node astNode) {
        //kmain doesn't do anything, just evaluate the code that follows
        for (Node child : ((InnerNode)astNode).getChildren()) {
            evaluateCode(child);
        }
        return null;
    }

    private static Object code26(Node astNode) {
        //kwdvars does nothing on its own, but is always followed by a VarList of vars, so we need to evaluate them
        if (astNode instanceof InnerNode) {
            for (Node n : ((InnerNode) astNode).getChildren()) {
                evaluateCode(n);
            }
        } else {
            throw new InterpreterException("kwdvars need to have a child node, but none found");
        }
        return null;
    }

    private static Object code45(Node astNode) {
        if (astNode instanceof InnerNode) {
            //assign the righthand expression to the lefthand id
            SymtabEntry target = (SymtabEntry) evaluateCode(((InnerNode) astNode).getChild(0));
            target.setValue(evaluateCode(((InnerNode) astNode).getChild(1)));
            return target.getValue(); // c style return
        } else {
            throw new InterpreterException("equal needs to have two child nodes, but none found");
        }
    }

    private static Object code47(Node astNode) {
        //add the two children together
        InnerNode node = (InnerNode) astNode;
        //get left child and unbox if it's an ID
        Object left = evaluateCode(node.getChild(0));
        if (left instanceof SymtabEntry) {
            left = ((SymtabEntry) left).getValue();
        }
        //get right child and unbox if it's an ID
        Object right = evaluateCode(node.getChild(1));
        if (right instanceof SymtabEntry) {
            right = ((SymtabEntry) right).getValue();
        }

        //add them together with the correct precision
        if (left instanceof Double && right instanceof Double) {
            return (Double) left + (Double) right;
        } else if (left instanceof Double) {
            return (Double) left + (Integer) right;
        } else if (right instanceof Double) {
            return (Integer) left + (Double) right;
        } else {
            return (Integer)left + (Integer) right;
        }
    }
}
