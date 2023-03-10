package ditto.lexer;

%%
%cup
%line
%column
%class Lexer
%unicode
%public

%{
  private LexerOperations ops;
  private ErrorHandler errores;
  public String lexeme() {return yytext();}
  public int row() {return yyline+1;}
  public int col() {return yycolumn+1;}
  public void setErrorHandler(ErrorHandler errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.scanEOF();
%eofval}

%init{
  ops = new LexerOperations(this);
%init}

/* Tokens ignorados */
separator = [ \t\r\b\n]
comment = \/\/[^\n]* 


/* Literales */
digit = [0-9]
nonZeroDigit = [1-9]
natural = ({nonZeroDigit}{digit}*|0)

true = true
false = false
boolean = {true}|{false}

/* Operadores binarios */
plus = \+
minus = \-
times = \*
div = \/
mod = %

greaterThan = \>
greaterEqual = \>=
lessThan = \<
lessEqual = \<=
equal = \=\=
notEqual = \!\=

not = not
and = and
or = or

openPar = \(
closePar = \)


%%
{separator}                   {}
{comment}                     {}

{natural}                     {return ops.scanNatural();}
{plus}                        {return ops.scanPlus();}
{minus}                       {return ops.scanMinus();}
{times}                       {return ops.scanTimes();}
{div}                         {return ops.scanDiv();}
{mod}                         {return ops.scanMod();}
{openPar}                     {return ops.scanOpenPar();}
{closePar}                    {return ops.scanClosePar();}
{greaterThan}                 {return ops.scanGreaterThan();}
{greaterEqual}                {return ops.scanGreaterEqual();}
{lessThan}                    {return ops.scanLessThan();}
{lessEqual}                   {return ops.scanLessEqual();}
{equal}                       {return ops.scanEqual();}
{notEqual}                    {return ops.scanNotEqual();}
{not}                         {return ops.scanNot();}
{and}                         {return ops.scanAnd();}
{or}                          {return ops.scanOr();}
{true}                        {return ops.scanTrue();}
{false}                       {return ops.scanFalse();}
[^]                           {errores.lexicalError(row(),col(),lexeme());}  