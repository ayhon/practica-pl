package ditto.errors;

import ditto.lexer.Token;

public class ErrorHandler {
    public void lexicalError(int row, int col, String lexeme) {
        System.out.println("ERROR row " + row + " column " + col + ": Caracter inesperado: " + lexeme);
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
        throw new SyntaxError(msg);
    }
}
