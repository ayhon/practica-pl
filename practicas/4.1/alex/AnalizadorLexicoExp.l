package alex;

import errors.GestionErroresExp;

%%
%cup
%column
%line
%class AnalizadorLexicoExp
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresExp errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresExp errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = {digitoPositivo}{digito}*
separador = [ \t\r\b\n]
comentario = #[^\n]* 
numeroEntero = [\+\-]?{parteEntera}
operadorSuma = \+
operadorMultiplicacion = \*
parentesisApertura = \(
parentesisCierre = \)
%%
{separador}               {}
{comentario}              {}
{numeroEntero}            {return ops.unidadEnt();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
[^]                       {errores.errorLexico(fila(),columna(),lexema());}  
