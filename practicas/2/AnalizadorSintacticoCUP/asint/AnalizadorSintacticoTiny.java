
//----------------------------------------------------
// The following code was generated by CUP v0.11b beta 20140220
// Tue Feb 14 18:17:43 UTC 2023
//----------------------------------------------------

package asint;

import java_cup.runtime.*;
import alex.AnalizadorLexicoTiny;
import alex.UnidadLexica;
import errors.GestionErroresTiny;
import java_cup.runtime.ComplexSymbolFactory.Location;

/** CUP v0.11b beta 20140220 generated parser.
  * @version Tue Feb 14 18:17:43 UTC 2023
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
    "\000\031\000\002\002\003\000\002\002\004\000\002\003" +
    "\004\000\002\003\003\000\002\004\004\000\002\004\005" +
    "\000\002\005\005\000\002\005\006\000\002\005\006\000" +
    "\002\005\005\000\002\005\003\000\002\006\003\000\002" +
    "\006\003\000\002\006\003\000\002\006\003\000\002\007" +
    "\003\000\002\007\003\000\002\007\003\000\002\007\003" +
    "\000\002\010\005\000\002\011\003\000\002\011\005\000" +
    "\002\011\002\000\002\012\003\000\002\012\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\051\000\006\004\007\020\004\001\002\000\012\016" +
    "\017\026\015\027\016\030\020\001\002\000\004\002\052" +
    "\001\002\000\004\002\001\001\002\000\004\012\012\001" +
    "\002\000\010\002\ufffe\004\007\020\004\001\002\000\004" +
    "\002\uffff\001\002\000\012\016\017\026\015\027\016\030" +
    "\020\001\002\000\012\002\ufff7\004\ufff7\020\ufff7\021\050" +
    "\001\002\000\010\002\ufffc\004\ufffc\020\ufffc\001\002\000" +
    "\012\006\037\007\041\010\043\011\042\001\002\000\012" +
    "\006\037\007\041\010\043\011\042\001\002\000\010\005" +
    "\030\016\017\017\uffeb\001\002\000\012\022\025\023\022" +
    "\024\023\025\024\001\002\000\004\005\026\001\002\000" +
    "\004\005\ufff1\001\002\000\004\005\ufff0\001\002\000\004" +
    "\005\uffef\001\002\000\004\005\ufff2\001\002\000\012\016" +
    "\017\026\015\027\016\030\020\001\002\000\010\002\ufff9" +
    "\004\ufff9\020\ufff9\001\002\000\006\013\uffea\017\uffea\001" +
    "\002\000\006\013\035\017\uffed\001\002\000\006\013\uffe9" +
    "\017\uffe9\001\002\000\004\017\034\001\002\000\016\002" +
    "\uffee\004\uffee\013\uffee\017\uffee\020\uffee\021\uffee\001\002" +
    "\000\010\005\030\016\017\017\uffeb\001\002\000\004\017" +
    "\uffec\001\002\000\014\005\ufff6\016\ufff6\026\ufff6\027\ufff6" +
    "\030\ufff6\001\002\000\004\005\044\001\002\000\014\005" +
    "\ufff5\016\ufff5\026\ufff5\027\ufff5\030\ufff5\001\002\000\014" +
    "\005\ufff3\016\ufff3\026\ufff3\027\ufff3\030\ufff3\001\002\000" +
    "\014\005\ufff4\016\ufff4\026\ufff4\027\ufff4\030\ufff4\001\002" +
    "\000\012\016\017\026\015\027\016\030\020\001\002\000" +
    "\010\002\ufffa\004\ufffa\020\ufffa\001\002\000\012\016\017" +
    "\026\015\027\016\030\020\001\002\000\010\002\ufffb\004" +
    "\ufffb\020\ufffb\001\002\000\012\016\017\026\015\027\016" +
    "\030\020\001\002\000\010\002\ufff8\004\ufff8\020\ufff8\001" +
    "\002\000\004\002\000\001\002\000\010\002\ufffd\004\ufffd" +
    "\020\ufffd\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\051\000\010\002\004\003\005\004\007\001\001\000" +
    "\006\005\052\010\012\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\006\003\010\004\007\001" +
    "\001\000\002\001\001\000\006\005\013\010\012\001\001" +
    "\000\002\001\001\000\002\001\001\000\004\006\045\001" +
    "\001\000\004\006\037\001\001\000\010\010\031\011\032" +
    "\012\030\001\001\000\004\007\020\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\006\005\026\010\012\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\010\010" +
    "\031\011\035\012\030\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\006\005\044\010\012\001\001" +
    "\000\002\001\001\000\006\005\046\010\012\001\001\000" +
    "\002\001\001\000\006\005\050\010\012\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001" });

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
          case 6: // EXPR ::= LREDUCE OPERARIT EXPR 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("EXPR",3, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // EXPR ::= LMAP OPERARIT NUM EXPR 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("EXPR",3, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // EXPR ::= LFILTER OPERBOOL NUM EXPR 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("EXPR",3, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // EXPR ::= LIST CONCAT EXPR 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("EXPR",3, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // EXPR ::= LIST 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("EXPR",3, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // OPERARIT ::= MAS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERARIT",4, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // OPERARIT ::= MENOS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERARIT",4, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // OPERARIT ::= POR 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERARIT",4, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // OPERARIT ::= DIV 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERARIT",4, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // OPERBOOL ::= LESS 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERBOOL",5, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // OPERBOOL ::= GREATER 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERBOOL",5, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // OPERBOOL ::= UNEQUAL 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERBOOL",5, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // OPERBOOL ::= EQUAL 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("OPERBOOL",5, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // LIST ::= LBEGIN INLIST LEND 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("LIST",6, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // INLIST ::= ELEMLIST 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("INLIST",7, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // INLIST ::= ELEMLIST COMA INLIST 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("INLIST",7, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // INLIST ::= 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("INLIST",7, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // ELEMLIST ::= NUM 
            {
              Object RESULT =null;

              CUP$AnalizadorSintacticoTiny$result = parser.getSymbolFactory().newSymbol("ELEMLIST",8, RESULT);
            }
          return CUP$AnalizadorSintacticoTiny$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // ELEMLIST ::= LIST 
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

