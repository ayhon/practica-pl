package ditto.ast;

public class Nat implements Node {
    public final int lexeme;

    Nat(String lexeme) {
        this.lexeme = Integer.parseInt(lexeme);
    }
}
