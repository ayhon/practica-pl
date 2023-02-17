package alex;

import errors.GestionErroresTiny;

%%
%cup
%line
%column
%class AnalizadorLexicoTiny
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = {digitoPositivo}{digito}*
parteDecimal = {digito}* {digitoPositivo}
separador = [ \t\r\b\n]
comentario = \/\/[^\n]* 
print = print
numeroEntero = [\+\-]?{parteEntera}
numeroReal = [\+\-]?{parteEntera}\.{parteDecimal}
numero = {numeroEntero}|{numeroReal}
identificador = {letra}({letra}|{digito})*
lreduce = lreduce
lfilter = lfilter
lmap = lmap

operadorConcat = \#
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
operadorMenor = \<
operadorMayor = \>
operadorIgual = \=\=
operadorDistinto = \!\=

inicioLista = \[
finLista = \]
coma = ,

parentesisApertura = \(
parentesisCierre = \)
asignacion = \=
%%
{separador}               {}
{comentario}              {}
{print}                   {return ops.unidadPrint();}
{lreduce}                 {return ops.unidadLReduce();}
{lfilter}                 {return ops.unidadLFilter();}
{lmap}                    {return ops.unidadLMap();}
{identificador}           {return ops.unidadId();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{asignacion}              {return ops.unidadAsignacion();} 
{coma}                    {return ops.unidadComa();}
{numero}                  {return ops.unidadNumero();}
{operadorConcat}          {return ops.unidadConcat();}
{operadorMenor}           {return ops.unidadMenor();}
{operadorMayor}           {return ops.unidadMayor();}
{operadorIgual}           {return ops.unidadIgual();}
{operadorDistinto}        {return ops.unidadDistinto();}
{inicioLista}             {return ops.unidadInicioLista();}
{finLista}                {return ops.unidadFinLista();}


[^]                       {errores.errorLexico(fila(),columna(),lexema());}  
//{evalua}                  {return ops.unidadEvalua();}
//{donde}                   {return ops.unidadDonde();}
//{numeroEntero}            {return ops.unidadEnt();}
//{numeroReal}              {return ops.unidadReal();}
