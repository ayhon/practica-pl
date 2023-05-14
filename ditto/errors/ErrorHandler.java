package ditto.errors;

import ditto.lexer.Token;

public class ErrorHandler {
    private int errors = 0;
    public boolean hasErrors() {
        return errors > 0;
    }

    public void lexicalError(int row, int col, String lexeme) {
        System.err.println("ERROR row " + row + " column " + col + ": Caracter inesperado: " + lexeme);
        errors++;
    }

    public void syntaxError(Token token) {
        String msg;
        if (token.lexeme() != null) {
            msg = "ERROR row " + token.row() + " col " + token.col()
                    + ": Unexpected element \"" + token.lexeme() + "\"";
        } else {
            msg = "ERROR row " + token.row() + " col " + token.col()
                    + ": Unexpected element";
        }
        System.err.println(msg);
        errors++;
    }
}