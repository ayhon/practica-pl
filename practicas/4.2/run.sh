LIB="../../lib"

cd eval

java -cp ../$LIB/cup.jar:. java_cup.Main -parser ConstructorASTExp -symbols ClaseLexica -nopositions Eval.cup

cd ..
javac -cp "$LIB/cup.jar:." */*.java
java -cp "$LIB/cup.jar:." eval.Main  input.txt