java -cp cup.jar java_cup.Main -parser AnalizadorSintacticoTiny -symbols ClaseLexica -nopositions Tiny.cup

echo "Copiando archivos generados por CUP a la carpeta AnalizadorSintacticoCUP/asint"
mv AnalizadorSintacticoTiny.java AnalizadorSintacticoCUP/asint
mv ClaseLexica.java AnalizadorSintacticoCUP/asint

echo "Compilanddo Java"
(cd AnalizadorSintacticoCUP && javac -cp "../cup.jar" */*.java)

echo "Ejecutando el analizador sintactico"
(cd AnalizadorSintacticoCUP && java -cp ".:../cup.jar" asint.Main ../input.txt)