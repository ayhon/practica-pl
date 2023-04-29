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
    test)
        task="$2"
        test_file="$3"
        case $test_file in
            all)
                for file in test/*.ditto; do 
                    $0 test $task `basename $file .ditto` || (echo "\e[31mFailed test $file\e[m" && exit 1); 
                done
                ;;
            *)
                file=`[ -z $test_file ] && echo "test/keywords.ditto" || echo "test/$test_file.ditto"`
                javac -cp "$JAR_PATH/*:." ditto/*/*.java ditto/*.java \
                && java -cp "$JAR_PATH/*:." ditto.Test $task $file
                ;;
        esac
        ;;
    
    *)
        echo "Nou entiendo"
        exit 1
        ;;
esac