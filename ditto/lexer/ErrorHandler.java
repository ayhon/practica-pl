package ditto.lexer;

public class ErrorHandler {
    public void lexicalError(int row, int col, String lexeme) {
        System.out.println("ERROR row " + row + " column " + col + ": Caracter inesperado: " + lexeme);
        System.exit(1);
    }

    // public void syntaxEror(UnidadLexica unidadLexica) {
    //     if (unidadLexica.lexema() != null) {
    //         System.out.println("ERROR fila " + unidadLexica.fila() + " columna " + unidadLexica.columna()
    //                 + ": Elemento inesperado \"" + unidadLexica.lexema() + "\"");
    //     } else {
    //         System.out.println(
    //                 "ERROR fila " + unidadLexica.fila() + " columna " + unidadLexica.columna()
    //                         + ": Elemento inesperado");
    //     }
    //     System.exit(1);
    // }
}
