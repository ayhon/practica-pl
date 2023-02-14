green_echo(){
    echo "\e[32m$1\e[m"
}
red_echo(){
    echo "\e[31m$1\e[m"
}
blue_echo(){
    echo "\e[34m$1\e[m"
}
exit_loudly(){
    red_echo "Fix this!"
    exit 1
}
victory(){
    blue_echo "DONE!"
    sleep 1
    curl parrot.live
}
jflex(){
    java -cp jflex.jar jflex.Main $1
}

green_echo "Generando parser con CUP"
java -cp cup.jar java_cup.Main -parser AnalizadorSintacticoTiny -symbols ClaseLexica -nopositions Tiny.cup || exit_loudly

green_echo "Copiando archivos generados por CUP a la carpeta AnalizadorSintacticoCUP/asint"
mv AnalizadorSintacticoTiny.java AnalizadorSintacticoCUP/asint
mv ClaseLexica.java AnalizadorSintacticoCUP/asint

green_echo "Generando lexer con JFLEX"
(jflex AnalizadorLexicoTiny.l && mv AnalizadorLexicoTiny.java AnalizadorSintacticoCUP/alex) || exit_loudly

green_echo "Compilando Java"
(cd AnalizadorSintacticoCUP && javac -cp "../cup.jar" */*.java) || exit_loudly

green_echo "Ejecutando el analizador sintactico"
(cd AnalizadorSintacticoCUP && java -cp ".:../cup.jar" asint.Main ../input.txt) || exit_loudly

victory