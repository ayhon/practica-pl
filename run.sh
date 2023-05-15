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
usage(){
        cat <<EOF
Uso: $0 [build|compile|test] 
    build [lexer|parser|java|all]: Compila las partes del proyecto
        lexer: Genera el lexer ditto/lexer/Lexer.java a partir de ditto/lexer/Tokens.l
        parser: Genera el parser ditto/parser/Parser.java y los tokens ditto/parser/TokenKind.java
                a partir de ditto/parser/Syntax.cup
        java: Compila el codigo java del compilador
        all: Genera y compila todo

    compile [<filepath>]: Compila el fichero .ditto especificado en <filepath>
                    Se pueden encontrar programas de ejemplo en test/pass/*.ditto

    test [action] [<filepath>|all]: ejecuta los tests de la etapa [action]
        ast: ejecuta los tests de construcción del ast
        bind: ejecuta los tests de vinculación
        typesize: ejecuta los tests de cálculo de tamaño de tipos
        typecheck: ejecuta los tests de tipado
        offsets: ejecuta los tests de cáluculo de desplazamientos
        code: ejecuta los tests de compilación

        all: Ejecuta los tests de "test/pass" y "test/fail/action". Se puede omitir
        <filepath>: Ejecuta el test para el fichero especificado
EOF
}

case $1 in
    help)
        usage && exit 0
        ;;
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
                echo "Opción no válida para \"build\": \"$2\""
                usage && exit 1
                ;;
        esac
        ;;
    compile)
        file="$2"
		print_ast="$3" # If "ast", prints the AST
        $0 build all && \
        filename=`basename $file .ditto`
        filedir=`dirname $file`
        ditto_test code $file $3 && ./wat2wasm "$filedir/compiled/$filename.wat" -o "WASM/$filename.wasm"
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
                    if [ "$task" == "code" ] && ! grep -q "func main" "$test_file"
                    then
                        echo "👌 skipping [$task] $test_file porque no tiene func main"
                        # No compilar los ficheros que no tienen funcion main
                        continue
                    fi
                    ditto_test $task $test_file $print_ast
                    RESULT=$?

                    # Compilar y ejecutar el codigo generado si la tarea es code
                    if [ $RESULT -eq 0 ] && [ "$task" == "code" ]; then
                        # Tiene mismo nombre, pero extension .wat, en carpeta compiled
                        dir=$(dirname $test_file)
                        wat_file=$(basename $test_file .ditto).wat
                        node main.js $dir/compiled/$wat_file --disable-console-print
                        RESULT=$?
                    fi

                    if [ $RESULT -ne 0 ]; then
                        echo "❌ failed [$task] $test_file"
                        failure="yes"
                    else
                        echo "✅ passed [$task] $test_file"
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
                                failure="yes"
                            else 
                                echo "      ✅ failed [$task_i] $test_file  y deberia"
                            fi
                        # si no, deberia pasar
                        else
                            if [ $RESULT -ne 0 ]; then
                                # echo "$test_output"
                                echo "      ❌ failed [$task_i] $test_file  y NO deberia"
                                failure="yes"
                            else
                                echo "      ✅ passed [$task_i] $test_file  y deberia"
                            fi
                        fi

                        # Pasar al siguiente fichero cuando se ha ejecutado la tarea $task
                        if [ $task_i == $task ]; then
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
                
                if [ $? -eq 0 ] && [ "$task" == "code" ]; then
                    # Ejecutar el codigo generado
                    # Tiene mismo nombre, pero extension .wat, en carpeta compiled
                    dir=$(dirname $test_file)
                    wat_file=$(basename $test_file .ditto).wat
                    node main.js $dir/compiled/$wat_file
                fi
                ;;
        esac
        ;;
    
    *)
        usage && exit 1
        ;;
esac
