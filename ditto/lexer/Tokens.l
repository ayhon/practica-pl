package ditto.lexer;

import ditto.errors.ErrorHandler;

%%
%unicode
%line
%column
%class Lexer
%public
/*%type Token*/
/*Descomentar cuando se quiera poner con CUP */
%cup

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

openBracket = \[
closeBracket = \]

openCurly  = \{
closeCurly = \}

rArrow = ->

/* Variables */
letter = [a-zA-Z_]
identifier = {letter}({letter}|{digit})*

assign = :\=
semicolon = \;
comma = ,
colon = :
quadot = ::
dot = \.
at = @
hash = #

/* Keywords */
int = int
bool = bool
func = func
if = if
else = else
for = for
while = while
import = import
return = return
match = match
case = case
is = is 
otherwise = otherwise
end = end
do = do
then = then
struct = struct
from = from
to = to 
by = by
ptr = ptr 
ref = ref
array = array
new = new
null = null


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
{openBracket}                 {return ops.scanOpenBracket();}
{closeBracket}                {return ops.scanCloseBracket();}
{openCurly}                   {return ops.scanOpenCurly();}
{closeCurly}                  {return ops.scanCloseCurly();}
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
{null}                        {return ops.scanNull();  }

{semicolon}                   {return ops.scanSemicolon();}
{colon}                       {return ops.scanColon();}
{comma}                       {return ops.scanComma();}
{rArrow}                      {return ops.scanRArrow();}
{quadot}                      {return ops.scanQuadot();}
{dot}                         {return ops.scanDot();}
{at}                          {return ops.scanAt();}
{assign}                      {return ops.scanAssign();}
{hash}                        {return ops.scanHash();}

{int}                         {return ops.scanInt();}
{bool}                        {return ops.scanBool();}
{func}                        {return ops.scanFunc();}
{if}                          {return ops.scanIf();}
{else}                        {return ops.scanElse();}
{for}                         {return ops.scanFor();}
{while}                       {return ops.scanWhile();}
{import}                      {return ops.scanImport();}
{return}                      {return ops.scanReturn();}
{match}                       {return ops.scanMatch();}
{case}                        {return ops.scanCase();}
{is}                          {return ops.scanIs();}
{otherwise}                   {return ops.scanOtherwise();}
{end}                         {return ops.scanEnd();}
{do}                          {return ops.scanDo();}
{then}                        {return ops.scanThen();}
{struct}                      {return ops.scanStruct();}
{from}                        {return ops.scanFrom();}
{to}                          {return ops.scanTo();}
{by}                          {return ops.scanBy();}
{ptr}                         {return ops.scanPtr();}
{ref}                         {return ops.scanRef();}
{array}                       {return ops.scanArray();}
{new}                         {return ops.scanNew();}
{identifier}                  {return ops.scanIdentifier();}
[^]                           {errores.lexicalError(row(),col(),lexeme());}  