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
            java)
                find ditto -type f -name "*.class" -delete && \
                javac -cp "$JAR_PATH/*:." ditto/*/*.java ditto/*.java
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
    
        # Comprobar el flag de DO_NOT_COMPILE y compilar si no está
        if [ -z $DO_NOT_COMPILE ]; then
            $0 build all
        fi && \

        case $test_file in
            all)
                # Y el resto de veces ejecuto los tests sin compilar
                export DO_NOT_COMPILE=1
                for file in test/*.ditto; do 
                    $0 test $task `basename $file .ditto` \
                        || exit 1; 
                done
                ;;
            *)
                file=`[ -z $test_file ] && echo "test/keywords.ditto" || echo "test/$test_file.ditto"`
                java -cp "$JAR_PATH/*:." ditto.Test $task $file \
                && (echo -e "\e[32mPassed test $file\e[m") \
                || (echo -e "\e[31mFailed test $file\e[m" && exit 1); 
                ;;
        esac
        ;;
    
    *)
        echo "Nou entiendo"
        exit 1
        ;;
esac