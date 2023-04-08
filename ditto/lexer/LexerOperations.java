package ditto.lexer;

import ditto.parser.TokenKind;
import ditto.ast.literals.*;

public class LexerOperations {
    private final Lexer lexer;
    LexerOperations(Lexer lexer){
        this.lexer = lexer;
    }
    /* TERMINALES CON REPRESENTACIÓN EN EL AST */
    Token scanNatural(){
        System.out.println("Token Natural");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.NAT, new Natural(lexer.lexeme()));
        // Pasamos aquí una instancia de `Natural` para que el parser pueda recibir un `Natural` cuando detecte el terminal
    }
    Token scanTrue(){
        System.out.println("Token True");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.TRUE, True.getInstance());
        // Pasamos aquí una instancia de `True` para que el parser pueda recibir un `True` cuando detecte el terminal
    }
    Token scanFalse(){
        System.out.println("Token False");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.FALSE, False.getInstance());
        // Pasamos aquí una instancia de `False` para que el parser pueda recibir un `False` cuando detecte el terminal
    }
    Token scanNull(){
        System.out.println("Token NULL");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.NULL, Null.getInstance());
        // Pasamos aquí una instancia de `Null` para que el parser pueda recibir un `False` cuando detecte el terminal
    }
    Token scanIdentifier(){
        System.out.println("Token Identifier");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.IDEN, lexer.lexeme());
        // Pasamos aquí `lexeme` para que el parser pueda recibir un `String` cuando detecte el terminal
    }
    /* TERMINALES SIN REPRESENTACIÓN EN EL AST */
    /** PUNTUACIÓN **/
    Token scanEOF(){
        System.out.println("Token EOF");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.EOF);
    }
    Token scanPlus(){
        System.out.println("Token Plus");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.PLUS);
    }
    Token scanMinus(){
        System.out.println("Token Minus");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.MINUS);
    }
    Token scanTimes(){
        System.out.println("Token Times");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.TIMES);
    }
    Token scanDiv(){
        System.out.println("Token Div");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.DIV);
    }
    Token scanMod(){
        System.out.println("Token Mod");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.MOD);
    }
    Token scanOpenPar(){
        System.out.println("Token OpenPar");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.OPEN_PAR);
    }
    Token scanClosePar(){
        System.out.println("Token ClosePar");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.CLOSE_PAR);
    }
    Token scanOpenBracket(){
        System.out.println("Token OpenBracket");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.OPEN_BRACKET);
    }
    Token scanCloseBracket(){
        System.out.println("Token CloseBracket");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.CLOSE_BRACKET);
    }
    Token scanOpenCurly(){
        System.out.println("Token OpenCurly");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.OPEN_CURLY);
    }
    Token scanCloseCurly(){
        System.out.println("Token CloseCurly");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.CLOSE_CURLY);
    }
    Token scanGreaterThan(){
        System.out.println("Token GreaterThan");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.GREATER);
    }
    Token scanGreaterEqual(){
        System.out.println("Token GreaterEqual");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.GREATER_EQUAL);
    }
    Token scanLessThan(){
        System.out.println("Token LessThan");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.LESS);
    }
    Token scanLessEqual(){
        System.out.println("Token LessEqual");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.LESS_EQUAL);
    }
    Token scanEqual(){
        System.out.println("Token Equal");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.EQUAL);
    }
    Token scanNotEqual(){
        System.out.println("Token NotEqual");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.NOT_EQUAL);
    }
    Token scanSemicolon(){
        System.out.println("Token Semicolon");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.SEMICOLON);
    }
    Token scanColon(){
        System.out.println("Token Colon");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.COLON);
    }
    Token scanComma(){
        System.out.println("Token Comma");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.COMMA);
    }
    Token scanRArrow(){
        System.out.println("Token RArrow");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.R_ARROW);
    }
    Token scanQuadot(){
        System.out.println("Token Quadot");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.QUADOT);
    }
    Token scanDot(){
        System.out.println("Token Dot");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.DOT);
    }
    Token scanAt(){
        System.out.println("Token At");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.AT);
    }
    Token scanAssign(){
        System.out.println("Token Assign"); 
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.ASSIGN);
    }
    /** PALABRAS RESERVADAS **/
    Token scanInt(){
        System.out.println("Token Int");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.INT);
    }
    Token scanBool(){
        System.out.println("Token Bool");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.BOOL);
    }
    Token scanNot(){
        System.out.println("Token Not");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.NOT);
    }
    Token scanAnd(){
        System.out.println("Token And");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.AND);
    }
    Token scanOr(){
        System.out.println("Token Or");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.OR);
    }
    Token scanFunc(){
        System.out.println("Token Func");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.FUNC);
    }
    Token scanIf(){
        System.out.println("Token If");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.IF);
    }
    Token scanElse(){
        System.out.println("Token Else");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.ELSE);
    }
    Token scanFor(){
        System.out.println("Token For");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.FOR);
    }
    Token scanWhile(){
        System.out.println("Token While");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.WHILE);
    }
    Token scanImport(){
        System.out.println("Token Import");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.IMPORT);
    }
    Token scanReturn(){
        System.out.println("Token Return");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.RETURN);
    }
    Token scanMatch(){
        System.out.println("Token Match");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.MATCH);
    }
    Token scanCase(){
        System.out.println("Token Case");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.CASE);
    }
    Token scanIs(){
        System.out.println("Token Is");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.IS);
    }
    Token scanOtherwise(){
        System.out.println("Token Otherwise");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.OTHERWISE);
    }
    Token scanEnd(){
        System.out.println("Token End");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.END);
    }
    Token scanDo(){
        System.out.println("Token Do");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.DO);
    }
    Token scanThen(){
        System.out.println("Token Then");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.THEN);
    }
    Token scanModule(){
        System.out.println("Token Module");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.MODULE);
    }
    Token scanStruct(){
        System.out.println("Token Struct");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.STRUCT);
    }
    Token scanPublic(){
        System.out.println("Token Public");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.PUBLIC);
    }
    Token scanFrom(){
        System.out.println("Token From");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.FROM);
    }
    Token scanTo(){
        System.out.println("Token To");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.TO);
    }
    Token scanBy(){
        System.out.println("Token By");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.BY);
    }
    Token scanPtr(){
        System.out.println("Token Ptr");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.PTR);
    }
    Token scanRef(){
        System.out.println("Token Ref");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.REF);
    }
    Token scanArray(){
        System.out.println("Token Array");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.ARRAY);
    }
    Token scanNew(){
        System.out.println("Token New");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.NEW);
    }
}