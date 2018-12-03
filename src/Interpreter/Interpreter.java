package Interpreter;

import Parser.Exceptions.ParserException;
import Parser.LLParseMachine;
import Tree.Ast.Node;

public class Interpreter {
    private Node ast;

    public static void main(String[] args) {
        LLParseMachine parser = new LLParseMachine();
        try {
            parser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        new Interpreter(parser.getAst()).createScopes();

    }

    private Interpreter(Node ast) {
        this.ast = ast;
    }

    private void createScopes() {
        SctBuilder sctBuilder = new SctBuilder();
        sctBuilder.buildSct(ast);
        sctBuilder.printScopeTree();
    }
}
