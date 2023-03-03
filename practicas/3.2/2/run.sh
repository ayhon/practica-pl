LIB="../../lib"
cd asint
java -cp ../$LIB/cup.jar java_cup.Main -parser AnalizadorSintacticoTiny -symbols ClaseLexica -nopositions TinyErrors.cup
cd ..
javac -cp "$LIB/cup.jar:." */*.java
# java -cp "$LIB/cup.jar:." asint.Main input.txt
java -cp "$LIB/cup.jar:." asint.Main input-error.txt