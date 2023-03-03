LIB="../../lib"
cd constructorast

java -cp ../$LIB/cup.jar java_cup.Main -parser ConstructorASTExp -symbols ClaseLexica -nopositions ConstructorAST.cup

cd ..

javac -cp "$LIB/cup.jar:." */*.java

java -cp "$LIB/cup.jar:." constructorast.Main  input.txt