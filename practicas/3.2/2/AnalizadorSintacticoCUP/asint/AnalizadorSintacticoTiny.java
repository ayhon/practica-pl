
//----------------------------------------------------
// The following code was generated by CUP v0.11b beta 20140220
// Fri Feb 17 12:04:30 UTC 2023
//----------------------------------------------------

package asint;

import java_cup.runtime.*;
import alex.AnalizadorLexicoTiny;
import alex.UnidadLexica;
import errors.GestionErroresTiny;
import java_cup.runtime.ComplexSymbolFactory.Location;

/** CUP v0.11b beta 20140220 generated parser.
  * @version Fri Feb 17 12:04:30 UTC 2023
  */
public class AnalizadorSintacticoTiny extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public AnalizadorSintacticoTiny() {super();}

  /** Constructor which sets the default scanner. */
  public AnalizadorSintacticoTiny(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public AnalizadorSintacticoTiny(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\036\000\002\002\003\000\002\002\004\000\002\003" +
    "\004\000\002\003\003\000\002\004\004\000\002\004\005" +
    "\000\002\005\003\000\002\013\005\000\002\013\006\000" +
    "\002\013\006\000\002\013\003\000\002\014\005\000\002" +
    "\014\003\000\002\015\003\000\002\015\003\000\002\015" +
    "\005\000\002\006\003\000\002\006\003\000\002\006\003" +
    "\000\002\006\003\000\002\007\003\000\002\007\003\000" +
    "\002\007\003\000\002\007\003\000\002\010\005\000\002" +
    "\011\003\000\002\011\005\000\002\011\002\000\002\012" +
    "\003\000\002\012\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\060\000\006\004\007\020\004\001\002\000\016\004" +
    "\017\014\015\016\016\022\023\023\024\024\013\001\002" +
    "\000\004\002\061\001\002\000\004\002\001\001\002\000" +
    "\004\012\012\001\002\000\010\002\ufffe\004\007\020\004" +
    "\001\002\000\004\002\uffff\001\002\000\016\004\017\014" +
    "\015\016\016\022\023\023\024\024\013\001\002\000\012" +
    "\025\056\026\053\027\054\030\055\001\002\000\012\002" +
    "\ufffb\004\ufffb\015\ufffb\020\ufffb\001\002\000\016\004\017" +
    "\014\015\016\016\022\023\023\024\024\013\001\002\000" +
    "\010\005\041\016\016\017\uffe6\001\002\000\014\002\ufff3" +
    "\004\ufff3\015\ufff3\020\ufff3\021\ufff3\001\002\000\012\002" +
    "\ufff7\004\ufff7\015\ufff7\020\ufff7\001\002\000\014\002\ufff4" +
    "\004\ufff4\015\ufff4\020\ufff4\021\ufff4\001\002\000\010\002" +
    "\ufffc\004\ufffc\020\ufffc\001\002\000\012\006\030\007\032" +
    "\010\034\011\033\001\002\000\012\006\030\007\032\010" +
    "\034\011\033\001\002\000\014\002\ufff5\004\ufff5\015\ufff5" +
    "\020\ufff5\021\026\001\002\000\010\004\017\014\015\016" +
    "\016\001\002\000\012\002\ufff6\004\ufff6\015\ufff6\020\ufff6" +
    "\001\002\000\012\004\ufff1\005\ufff1\014\ufff1\016\ufff1\001" +
    "\002\000\004\005\035\001\002\000\012\004\ufff0\005\ufff0" +
    "\014\ufff0\016\ufff0\001\002\000\012\004\uffee\005\uffee\014" +
    "\uffee\016\uffee\001\002\000\012\004\uffef\005\uffef\014\uffef" +
    "\016\uffef\001\002\000\010\004\017\014\015\016\016\001" +
    "\002\000\012\002\ufff9\004\ufff9\015\ufff9\020\ufff9\001\002" +
    "\000\010\004\017\014\015\016\016\001\002\000\012\002" +
    "\ufffa\004\ufffa\015\ufffa\020\ufffa\001\002\000\006\013\uffe5" +
    "\017\uffe5\001\002\000\006\013\046\017\uffe8\001\002\000" +
    "\006\013\uffe4\017\uffe4\001\002\000\004\017\045\001\002" +
    "\000\020\002\uffe9\004\uffe9\013\uffe9\015\uffe9\017\uffe9\020" +
    "\uffe9\021\uffe9\001\002\000\010\005\041\016\016\017\uffe6" +
    "\001\002\000\004\017\uffe7\001\002\000\004\015\051\001" +
    "\002\000\014\002\ufff2\004\ufff2\015\ufff2\020\ufff2\021\ufff2" +
    "\001\002\000\004\005\057\001\002\000\004\005\uffec\001" +
    "\002\000\004\005\uffeb\001\002\000\004\005\uffea\001\002" +
    "\000\004\005\uffed\001\002\000\010\004\017\014\015\016" +
    "\016\001\002\000\012\002\ufff8\004\ufff8\015\ufff8\020\ufff8" +
    "\001\002\000\004\002\000\001\002\000\010\002\ufffd\004" +
    "\ufffd\020\ufffd\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\060\000\010\002\004\003\005\004\007\001\001\000" +
    "\014\005\061\010\020\013\013\014\017\015\024\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\006\003\010\004\007\001\001\000\002\001\001\000\014" +
    "\005\021\010\020\013\013\014\017\015\024\001\001\000" +
    "\004\007\051\001\001\000\002\001\001\000\014\005\047" +
    "\010\020\013\013\014\017\015\024\001\001\000\010\010" +
    "\042\011\043\012\041\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\006" +
    "\036\001\001\000\004\006\030\001\001\000\002\001\001" +
    "\000\010\010\020\014\026\015\024\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\010\010\020\014" +
    "\035\015\024\001\001\000\002\001\001\000\010\010\020" +
    "\014\037\015\024\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\010\010\042\011\046\012\041\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\010\010\020\014\057" +
    "\015\024\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$AnalizadorSintacticoTiny$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$AnalizadorSintacticoTiny$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$AnalizadorSintacticoTiny$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


  /** User initialization code. */
  public void user_init() throws java.lang.Exception
    {
 
   errores = new GestionErroresTiny();
   AnalizadorLexicoTiny alex = (AnalizadorLexicoTiny)getScanner();
   alex.fijaGestionErrores(errores);

    }

  /** Scan to get the next Symbol. */
  public java_cup.runtime.Symbol scan()
    throws java.lang.Exception
    {
 return getScanner().next_token(); 
    }

 
   private GestionErroresTiny errores;
   public void syntax_error(Symbol unidadLexica) {
     errores.errorSintactico((UnidadLexica)unidadLexica);
   }

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$AnalizadorSintacticoTiny$actions {
  private final AnalizadorSintacticoTiny parser;

  /** Constructor */
  CUP$AnalizadorSintacticoTiny$actions(AnalizadorSintacticoTiny parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$AnalizadorSintacticoTiny$do_action_part00000000(
    int                        CUP$AnalizadorSintacticoTiny$act_num,
    java_cup.runtime.lr_parser CUP$AnalizadorSintacticoTiny$parser,
    java.util.Stack            CUP$AnalizadorSintacticoTiny$stack,
    int                        CUP$AnalizadorSintacticoTiny$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$AnalizadorSintacticoTiny$result;

      /* select the action based on the action number */
      switch (CUP$AnalizadorSintacticoTiny$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // S ::= STATs 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("S",0, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= S EOF 
            {
              Object RESULT =null;
		Object start_val = (Object)((java_cup.runtime.Symbol) CUP$AnalizadorSintacticoTiny$stack.elementAt(CUP$AnalizadorSintacticoTiny$top-1)).value;
		RESULT = start_val;
              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("$START",0, RESULT);
            }
          /* ACCEPT */
          CUP$AnalizadorSintacticoTiny$parser.done_parsing();
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // STATs ::= STAT STATs 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("STATs",1, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // STATs ::= STAT 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("STATs",1, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // STAT ::= PRINT EXPR 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("STAT",2, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // STAT ::= IDEN ASSIGN EXPR 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("STAT",2, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // EXPR ::= FUNCIONES 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("EXPR",3, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // FUNCIONES ::= LREDUCE OPERARIT SUMANDOS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("FUNCIONES",9, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // FUNCIONES ::= LMAP OPERARIT NUM SUMANDOS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("FUNCIONES",9, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // FUNCIONES ::= LFILTER OPERBOOL NUM SUMANDOS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("FUNCIONES",9, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // FUNCIONES ::= SUMANDOS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("FUNCIONES",9, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // SUMANDOS ::= FINAL CONCAT SUMANDOS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("SUMANDOS",10, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // SUMANDOS ::= FINAL 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("SUMANDOS",10, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // FINAL ::= LIST 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("FINAL",11, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // FINAL ::= IDEN 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("FINAL",11, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // FINAL ::= PAP EXPR PCIERRE 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("FINAL",11, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // OPERARIT ::= MAS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERARIT",4, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // OPERARIT ::= MENOS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERARIT",4, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // OPERARIT ::= POR 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERARIT",4, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // OPERARIT ::= DIV 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERARIT",4, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // OPERBOOL ::= LESS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERBOOL",5, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // OPERBOOL ::= GREATER 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERBOOL",5, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // OPERBOOL ::= UNEQUAL 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERBOOL",5, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // OPERBOOL ::= EQUAL 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERBOOL",5, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // LIST ::= LBEGIN INLIST LEND 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("LIST",6, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // INLIST ::= ELEMLIST 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("INLIST",7, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // INLIST ::= ELEMLIST COMA INLIST 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("INLIST",7, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // INLIST ::= 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("INLIST",7, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // ELEMLIST ::= NUM 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("ELEMLIST",8, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // ELEMLIST ::= LIST 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("ELEMLIST",8, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$AnalizadorSintacticoTiny$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$AnalizadorSintacticoTiny$do_action(
    int                        CUP$AnalizadorSintacticoTiny$act_num,
    java_cup.runtime.lr_parser CUP$AnalizadorSintacticoTiny$parser,
    java.util.Stack            CUP$AnalizadorSintacticoTiny$stack,
    int                        CUP$AnalizadorSintacticoTiny$top)
    throws java.lang.Exception
    {
              return CUP$AnalizadorSintacticoTiny$do_action_part00000000(
                               CUP$AnalizadorSintacticoTiny$act_num,
                               CUP$AnalizadorSintacticoTiny$parser,
                               CUP$AnalizadorSintacticoTiny$stack,
                               CUP$AnalizadorSintacticoTiny$top);
    }
}
