package alex;

import asint.ClaseLexica;

public class ALexOperations {
    private AnalizadorLexicoTiny alex;

    public ALexOperations(AnalizadorLexicoTiny alex) {
        this.alex = alex;
    }

    public UnidadLexica unidadId() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IDEN,
                alex.lexema());
    }

    public UnidadLexica unidadSuma() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAS);
    }

    public UnidadLexica unidadResta() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOS);
    }

    public UnidadLexica unidadMul() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.POR);
    }

    public UnidadLexica unidadDiv() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIV);
    }

    public UnidadLexica unidadPAp() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PAP);
    }

    public UnidadLexica unidadPCierre() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PCIERRE);
    }

    public UnidadLexica unidadComa() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMA);
    }
    public UnidadLexica unidadEof() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EOF);
    }
    public UnidadLexica unidadAsignacion(){
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ASSIGN);
    }

    // New ones
    public UnidadLexica unidadNumero() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NUM);
    }

	public UnidadLexica unidadConcat() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CONCAT);
    }

	public UnidadLexica unidadMenor() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LESS);
    }

	public UnidadLexica unidadMayor() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.GREATER);
    }

	public UnidadLexica unidadIgual() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EQUAL);
    }

	public UnidadLexica unidadDistinto() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.UNEQUAL);
    }

	public UnidadLexica unidadInicioLista() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LBEGIN);
    }

	public UnidadLexica unidadFinLista() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LEND);
    }
	public UnidadLexica unidadPrint() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PRINT);
    }
}
