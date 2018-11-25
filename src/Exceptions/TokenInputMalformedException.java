package Exceptions;

public class TokenInputMalformedException extends Exception {
    public TokenInputMalformedException(String input){
        super(input);
    }

    public TokenInputMalformedException(){
        super();
    }
}
