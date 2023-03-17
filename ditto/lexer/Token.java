package ditto.lexer;

import java_cup.runtime.Symbol;

public class Token extends Symbol {
    private String lexeme;
    private int row;
    private int col;
    private int kind;

    public Token(int col, int row, String lexeme, int kind) {
        super(kind);
        this.row = row;
        this.col = col;
        this.lexeme = lexeme;
        this.kind = kind;
    }

    public int sym() {
        return sym;
    }

    public String lexeme() {
        return lexeme;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    public int kind() {
        return kind;
    }
}
