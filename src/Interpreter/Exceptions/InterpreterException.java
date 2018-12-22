package Interpreter.Exceptions;

/**
 * This is an exception for interpreter errors
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class InterpreterException extends RuntimeException {
    public InterpreterException() {
        super();
    }

    public InterpreterException(String msg) {
        super(msg);
    }
}
