package Interpreter.Exceptions;


public class InterpreterException extends RuntimeException {
    public InterpreterException() {
        super();
    }

    public InterpreterException(String msg) {
        super(msg);
    }
}
