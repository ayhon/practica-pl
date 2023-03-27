#!/bin/sh
JAR_PATH="./lib";
jflex(){
    java -cp $JAR_PATH/jflex.jar jflex.Main $@ 
}
cup(){
    java -cp $JAR_PATH/cup.jar java_cup.Main $@
    #-parser AnalizadorSintacticoTiny -symbols ClaseLexica -nopositions TinyErrors.cup
}

case $1 in
    build)
        case $2 in
            lexer)
                jflex ditto/lexer/Tokens.l
                rm ditto/lexer/Lexer.java~
                ;;
            parser)
                cup -parser Parser -symbols TokenKind -nopositions ditto/parser/Syntax.cup && \
                  mv Parser.java ditto/parser/ && \
                  mv TokenKind.java ditto/parser/
                ;;
            all)
                for target in {lexer,parser}; do
                    $0 build $target
                done
                ;;
            *)
                echo "Nou entiendo"
                exit 1
                ;;
        esac
        ;;
    lexer)
        javac -cp "$JAR_PATH/*" ditto/lexer/*.java \
         && java -cp "$JAR_PATH/*:." ditto.lexer.Test test/expr-input.txt
        ;;
    parser)
        javac -cp "$JAR_PATH/*" ditto/*/*.java \
         && java -cp "$JAR_PATH/*:." ditto.parser.Test test/keywords.txt
        ;;
    *)
        echo "Nou entiendo"
        exit 1
        ;;
esac