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
                cup ditto/parser/Parser.cup
                ;;
            *)
                echo "Nou entiendo"
                exit 1
                ;;
        esac
        ;;
    lexer)
        echo "No hemos llegado ahí"
        stop
        ;;
    *)
        echo "Nou entiendo"
        exit 1
        ;;
esac