package alex;

import java_cup.runtime.Symbol;


public class UnidadLexica extends Symbol {
    public UnidadLexica(int fila, int columna, int clase) {
	super(clase,new TokenValue(fila,columna));
    }
    public UnidadLexica(int fila, int columna, int clase, String lexema) {
	super(clase,new TokenValue(lexema,fila,columna));
    }
    public int clase () {return sym;}
    public String lexema() {return ((TokenValue)value).lexema;}    
    public int fila() {return ((TokenValue)value).fila;}
    public int columna() {return ((TokenValue)value).columna;}
}
