package ditto.errors;

import ditto.lexer.Token;

public class ErrorHandler {
    public void lexicalError(int row, int col, String lexeme) {
        System.out.println("ERROR row " + row + " column " + col + ": Caracter inesperado: " + lexeme);
        System.exit(1);
    }

    public void syntaxError(Token token) {
        if (token.lexeme() != null) {
            System.out.println("ERROR row " + token.row() + " col " + token.col()
                    + ": Unexpected element \"" + token.lexeme() + "\"");
        } else {
            System.out.println(
                    "ERROR row " + token.row() + " col " + token.col()
                            + ": Unexpected elemendadwat");
        }
        System.exit(1);
    }
}
