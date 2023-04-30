package ditto.errors;

public class SyntaxError extends RuntimeException{
    SyntaxError(String msg){
        super(msg);
    }
}