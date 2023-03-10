package ditto.lexer;

public class LexerOperations {
    private final Lexer lexer;
    LexerOperations(Lexer lexer){
        this.lexer = lexer;
    }
    Token scanEOF(){
        System.out.println("Token EOF");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanNatural(){
        System.out.println("Token Natural");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanPlus(){
        System.out.println("Token Plus");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanMinus(){
        System.out.println("Token Minus");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanTimes(){
        System.out.println("Token Times");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanDiv(){
        System.out.println("Token Div");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanMod(){
        System.out.println("Token Mod");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanOpenPar(){
        System.out.println("Token OpenPar");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanClosePar(){
        System.out.println("Token ClosePar");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanGreaterThan(){
        System.out.println("Token GreaterThan");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanGreaterEqual(){
        System.out.println("Token GreaterEqual");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanLessThan(){
        System.out.println("Token LessThan");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanLessEqual(){
        System.out.println("Token LessEqual");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanEqual(){
        System.out.println("Token Equal");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanNotEqual(){
        System.out.println("Token NotEqual");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanNot(){
        System.out.println("Token Not");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanAnd(){
        System.out.println("Token And");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanOr(){
        System.out.println("Token Or");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanTrue(){
        System.out.println("Token True");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
    Token scanFalse(){
        System.out.println("Token False");
        return new Token(lexer.col(), lexer.row(), lexer.lexeme(), 0);
    }
}
