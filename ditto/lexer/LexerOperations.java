package ditto.lexer;

import ditto.parser.TokenKind;

public class LexerOperations {
    private final Lexer lexer;
    LexerOperations(Lexer lexer){
        this.lexer = lexer;
    }
    Token scanEOF(){
        System.out.println("Token EOF");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.EOF);
    }
    Token scanNatural(){
        System.out.println("Token Natural");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.NAT);
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
    Token scanTrue(){
        System.out.println("Token True");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.TRUE);
    }
    Token scanFalse(){
        System.out.println("Token False");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.FALSE);
    }
    Token scanSemicolon(){
        System.out.println("Token Semicolon");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.SEMICOLON);
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
    Token scanIdentifier(){
        System.out.println("Token Identifier");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.IDEN);
    }
    Token scanAssign(){
        System.out.println("Token Assign"); 
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), TokenKind.ASSIGN);
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