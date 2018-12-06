package Interpreter;

import Parser.Exceptions.ParserException;
import Parser.LLParseMachine;
import Tree.Ast.Node;

public class Interpreter {
    private Node ast;
    private SctBuilder sctBuilder;

    public static void main(String[] args) {
        LLParseMachine parser = new LLParseMachine();
        try {
            parser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        new Interpreter(parser.getAst()).interpret();
    }

    private Interpreter(Node ast) {
        this.ast = ast;
    }

    private void interpret() {
        createScopes();
        EvaluationFunctions.evaluateCode(ast);
        sctBuilder.printScopeTree();
    }

    private void createScopes() {
        sctBuilder = new SctBuilder();
        sctBuilder.buildSct(ast);
        sctBuilder.printScopeTree();
    }
}
