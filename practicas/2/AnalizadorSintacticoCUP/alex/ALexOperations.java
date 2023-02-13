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

    public UnidadLexica unidadIgual() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IGUAL);
    }

    public UnidadLexica unidadComa() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMA);
    }

    public UnidadLexica unidadEof() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EOF);
    }
    // public UnidadLexica unidadNumero() {
    // return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.);
    // }
    // public UnidadLexica unidadConcat() {
    // return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.);
    // }
    // public UnidadLexica unidadMenor() {
    // return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.);
    // }
    // public UnidadLexica unidadMayor() {
    // return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.);
    // }
    // public UnidadLexica unidadIgual() {
    // return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.);
    // }
    // public UnidadLexica unidadDistinto() {
    // return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.);
    // }
    // public UnidadLexica unidadInicioLista() {
    // return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.);
    // }
    // public UnidadLexica unidadFinLista() {
    // return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.);
    // }
    // public UnidadLexica unidadPrint() {
    // return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.);
    // }

}
