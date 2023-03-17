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
}
