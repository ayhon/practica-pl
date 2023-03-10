package ditto.lexer;

import java_cup.runtime.Symbol;

public class Token extends Symbol {
    public String lexeme;
    public int row;
    public int col;
    
    public Token(int col, int row, String lexeme, int kind) {
        super(kind);
    	this.row = row;
    	this.col = col;
        this.lexeme = lexeme;
    }
}
