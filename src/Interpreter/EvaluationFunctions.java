package Interpreter;

import Interpreter.Exceptions.InterpreterException;
import Tree.Ast.InnerNode;
import Tree.Ast.Node;

public class EvaluationFunctions {

    public static void evaluateCode(Node astNode) {
        switch (astNode.getSymbol().getId()) { //check which ID to use
            //TODO implement all the handlers
            case 26: //KVAR
                code26(astNode);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static void code26(Node astNode) {
        //kwdvars does nothing on its own, but is always followed by a PPvarlist of vars, so we need to evaluate it
        if (astNode instanceof InnerNode) {
            evaluateCode(((InnerNode)astNode).getChild(0));
        } else {
            throw new InterpreterException("kwdvars need to have a child node, but none found");
        }
    }
}
