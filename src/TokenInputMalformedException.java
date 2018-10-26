class TokenInputMalformedException extends Exception {
    TokenInputMalformedException(String input){
        super(input);
    }
    TokenInputMalformedException(){
        super();
    }
}
