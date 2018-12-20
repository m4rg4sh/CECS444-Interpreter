package Interpreter;

import Interpreter.Exceptions.InterpreterException;
import Interpreter.Symtab.SymtabEntry;
import Symbols.Terminal;
import Tokens.FloatToken;
import Tokens.IntToken;
import Tree.Ast.InnerNode;
import Tree.Ast.Node;


public class EvaluationFunctions {

    public static Object evaluateCode(Node astNode) {
        switch (astNode.getToken().getId()) { //check which ID to use
            case 1: //COMMENT
                return null;
            //TODO implement all the handlers
            case 2: //ID
                return code2(astNode);
            case 3: //INT
                return code3(astNode);
            case 4: //FLOAT
                return code4(astNode);
            case 5: //STRING
                return code5(astNode);
            case 6: //COMMA
                //fallthrough
            case 7: throwException(astNode);
                    return null;
            case 10: //KPROG
                return code10(astNode);
            case 11: //KMAIN
                return code11(astNode);
            case 12: //KFCN
                return code12(astNode);
            case 13: //KCLASS
                return code13(astNode);
            case 15: //KFLOAT
                return code15(astNode);
            case 16: //KINT
                return code16(astNode);
            case 17: //KSTRING
                return code17(astNode);
            case 18: //KIF
                return code18(astNode);
            case 19: //KELSEIF
                return code19(astNode);
            case 20: //KELSE
                return code20(astNode);
            case 21: //KWHILE
                return code21(astNode);
            case 23: //KPRINT
                return code23(astNode);
            case 26: //KVAR
                return code26(astNode);
            case 31: //Angle1
                return code31(astNode);
            case 33: //Brace1
                return code33(astNode);
            case 37: //Paren1
                return code37(astNode);
            case 45: //EQUAL
                return code45(astNode);
            case 47: //PLUS
                return code47(astNode);
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static void throwException(Node astNode) {
        String symbol = Terminal.valueOf(astNode.getToken().getId()).toString();
        throw new InterpreterException("There should not be a " + symbol + " left by now, however there it is and" +
                "now we don't know what to do with it");
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

    private static Object code5(Node astNode) {
        //just return the value
        return astNode.getToken().getCodeString();
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

    private static Object code12 (Node astNode) {
        //TODO implement KFCN
        //first child is the ID
        //second child is the param list
        //third child is return type (which we can ignore for now)
        //fourth child is the code block that we need to execute when the function gets called

        //basic idea: we can link the value variable in the scope tree back to the AST node of the code block
        //this way we can call the interpreter on it
        //before the function call we need to somehow initialize the variables and afterwards we might want to reset them

        return null;
    }

    private static Object code13 (Node astNode) {
        //TODO implement KCLASS
        throw new UnsupportedOperationException();
    }

    private static Object code15 (Node astNode) {
        //TODO implement KFLOAT
        //if we don't do typechecking we can ignore this
        throw new UnsupportedOperationException();
    }

    private static Object code16 (Node astNode) {
        //TODO implement KINT
        //if we don't do typechecking we can ignore this
        throw new UnsupportedOperationException();
    }

    private static Object code17 (Node astNode) {
        //TODO implement KSTRING
        //if we don't do typechecking we can ignore this
        throw new UnsupportedOperationException();
    }

    private static Object code18 (Node astNode) {
        //TODO implement KIF
        throw new UnsupportedOperationException();
    }

    private static Object code19 (Node astNode) {
        //TODO implement KELSEIF
        throw new UnsupportedOperationException();
    }

    private static Object code20 (Node astNode) {
        //TODO implement KELSE
        throw new UnsupportedOperationException();
    }

    private static Object code21 (Node astNode) {
        // evaluate child 0 (condition)
        // if true, execute child 1, else return
        while ((Boolean) evaluateCode(((InnerNode) astNode).getChild(0))) {
            evaluateCode(((InnerNode)astNode).getChild(1));
        }
        return null;
    }

    private static Object code23(Node astNode) {
        if (astNode instanceof InnerNode) {
            for (Node n : ((InnerNode) astNode).getChildren()) {
                Object item = evaluateCode(n);
                if (item instanceof SymtabEntry) {
                    item = ((SymtabEntry) item).getValue();
                }
                System.out.print(item);
            }
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

    private static Object code31(Node astNode) {
        InnerNode node = (InnerNode) astNode;

        //evaluate both kids against each other

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
            return (Double) left < (Double) right;
        } else if (left instanceof Double) {
            return (Double) left < (Integer) right;
        } else if (right instanceof Double) {
            return (Integer) left < (Double) right;
        } else {
            return (Integer)left < (Integer) right;
        }
    }

    private static Object code33(Node astNode) {
        //just execute whatever comes next if there even is something
        if (astNode instanceof InnerNode) {
            for (Node n : ((InnerNode) astNode).getChildren()) {
                evaluateCode(n);
            }
        }
        return null;
    }

    private static Object code37(Node astNode) {
        //just execute whatever comes next if there even is something
        if (astNode instanceof InnerNode) {
            for (Node n : ((InnerNode) astNode).getChildren()) {
                evaluateCode(n);
            }
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
