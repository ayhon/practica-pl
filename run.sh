#!/bin/bash
JAR_PATH="./lib";
TASKS=(
    "ast" 
    "bind" 
    "typesize" 
    "typecheck" 
    "offsets" 
    "code"
);

jflex(){
    java -cp $JAR_PATH/jflex.jar jflex.Main $@ 
}
cup(){
    java -cp $JAR_PATH/cup.jar java_cup.Main $@
    #-parser AnalizadorSintacticoTiny -symbols ClaseLexica -nopositions TinyErrors.cup
}
ditto_test(){
    java -cp "$JAR_PATH/*:." ditto.Test $@
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
                $0 build lexer && $0 build parser && $0 build java
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
        print_ast="$4"

        # Comprobar el flag de DO_NOT_COMPILE y compilar si no está
        if [ -z $DO_NOT_COMPILE ]; then
            $0 build all
        fi && \

        case $test_file in
            all)
                # Y el resto de veces ejecuto los tests sin compilar
                export DO_NOT_COMPILE=1
                failure="no"

                # Ejecutar todos los casos de prueba que hay en test/pass/*.ditto
                # Mediante un for
                echo "🧪 Ejecutando los tests que deberian pasar"
                for test_file in test/pass/*.ditto; do
                    ditto_test $task $test_file $print_ast
                    if [ $? -ne 0 ]; then
                        echo "❌ failed $test_file - $task"
                        failue="yes"
                    else
                        echo "✅ passed $test_file - $task"
                    fi
                done

                # Si ejecuto las tareas que hay en test/fail/$task/*.ditto deberian de fallar,
                # Hay que ejecutar todas las tareas que van antes de $task, y deberian de pasar sin problema
                # Y cuando llega a la tarea $task deberia de fallar, y terminar
                # Iterar por fichero fuera, y dentro por tareas
                echo "🧪 Ejecutando los tests que deberian fallar"
                for test_file in test/fail/$task/*.ditto; do
                    [ -f $test_file ] || break # Si no hay ficheros, salir
                    echo "  📁 Ejecutando $test_file"
                    for task_i in "${TASKS[@]}"; do
                        test_output=$(ditto_test $task_i $test_file $print_ast 2>&1)
                        RESULT=$?
                        # Deberia fallar la ejecucion si la tarea es $task
                        # Si no falla, lanza un mensaje de error
                        if [ $task_i == $task ]; then
                            if [ $RESULT -eq 0 ]; then
                                # echo "$test_output"
                                echo "      ❌ passed [$task_i] $test_file  y NO deberia"
                                failue="yes"
                            else 
                                echo "      ✅ failed [$task_i] $test_file  y deberia"
                            fi
                        # si no, deberia pasar
                        else
                            if [ $RESULT -ne 0 ]; then
                                # echo "$test_output"
                                echo "      ❌ failed [$task_i] $test_file  y NO deberia"
                                failue="yes"
                            else
                                echo "      ✅ passed [$task_i] $test_file  y deberia"
                            fi
                        fi

                        # Pasar al siguiente fichero cuando se ha ejecutado la tarea $task
                        if [ $task == $task ]; then
                            break
                        fi
                    done
                done
                if [ "$failure" == "yes" ]; then
                    exit 1
                else
                    echo "🎉 ¡Hecho!"
                fi
                ;;
            *)
                ditto_test $task $test_file $print_ast
                ;;
        esac
        ;;
    
    *)
        echo "Nou entiendo"
        exit 1
        ;;
esac