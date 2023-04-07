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
                rm -f ditto/lexer/Lexer.java~
                ;;
            parser)
                cup -parser Parser -symbols TokenKind -nopositions ditto/parser/Syntax.cup && \
                  mv Parser.java ditto/parser/ && \
                  mv TokenKind.java ditto/parser/
                ;;
            all)
                $0 build lexer && $0 build parser
                ;;
            *)
                echo "Nou entiendo"
                exit 1
                ;;
        esac
        ;;
    lexer)
        javac -cp "$JAR_PATH/*:." ditto/lexer/*.java \
         && java -cp "$JAR_PATH/*:." ditto.lexer.Test test/expr-input.txt
        ;;
    parser)
        javac -cp "$JAR_PATH/*:." ditto/*/*.java \
         && java -cp "$JAR_PATH/*:." ditto.parser.Test test/keywords.ditto
        ;;
    ast)
        file=`[ -z $2 ] && echo "test/keywords.ditto" || echo "test/$2.ditto"`
        javac -cp "$JAR_PATH/*:." ditto/*/*.java \
         && java -cp "$JAR_PATH/*:." ditto.ast.Test $file
        ;;
    test-ast)
        # Iterar sobre los archivos de la carpeta test e invocar ast
        for file in test/*.ditto; do
            # Solo pasamos el nombre del fichero, sin extension
            echo "Mostrando AST sobre $file"
            $0 ast `basename $file .ditto`
            echo "============================"
        done
        ;;
    *)
        echo "Nou entiendo"
        exit 1
        ;;
esac