package alex;

public class TokenValue {
    public String lexema;
    public int fila;
    public int columna;
    
    public TokenValue(int fila, int columna) {
    	this.fila = fila;
    	this.columna = columna;
    }
    public TokenValue(String lexema, int fila, int columna) {
    	this.lexema = lexema;
    	this.fila = fila;
    	this.columna = columna;
    }
}

