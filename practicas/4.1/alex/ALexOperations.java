package alex;

import constructorast.ClaseLexica;

public class ALexOperations {
  private AnalizadorLexicoExp alex;
  public ALexOperations(AnalizadorLexicoExp alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadEnt() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.NUM,alex.lexema()); 
  } 
  public UnidadLexica unidadSuma() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAS,"+"); 
  } 
  public UnidadLexica unidadMul() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POR,"*"); 
  } 
  public UnidadLexica unidadPAp() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PAP,"("); 
  } 
  public UnidadLexica unidadPCierre() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PCIERRE,")"); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EOF,"<EOF>"); 
  }
}
