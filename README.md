# Práctica PL

Implementación de un compilador de `ditto` a WASM. Práctica de la asignatura *Procesadores de Lenguaje*

## Uso

Para probar el compilador, usar el script `run.sh`

```
Uso: ./run.sh [build|compile|test] 
    build [lexer|parser|java|all]: Compila las partes del proyecto
        lexer: Genera el lexer ditto/lexer/Lexer.java a partir de ditto/lexer/Tokens.l
        parser: Genera el parser ditto/parser/Parser.java y los tokens ditto/parser/TokenKind.java
                a partir de ditto/parser/Syntax.cup
        java: Compila el codigo java del compilador
        all: Genera y compila todo

    compile [path]: Compila el fichero .ditto especificado en path
                    Se pueden encontrar programas de ejemplo en test/pass/*.ditto

    test: ejecuta los tests
        ast: ejecuta los tests de construcción del ast
        bind: ejecuta los tests de vinculación
        typesize: ejecuta los tests de cálculo de tamaño de tipos
        typecheck: ejecuta los tests de tipado
        offsets: ejecuta los tests de cáluculo de desplazamientos
        code: ejecuta los tests de compilación
```

## Estructura

 - `ditto`: Código del compilador.
   - `ditto/ast`: Definición de nodos del AST.
   - `ditto/parser`: Gramática CUP junto con parser generado.
   - `ditto/lexer`: Definición de tokens `.l` para JFLEX y lexer generado.
 - `run.sh`: Script de ejecución.
 - `test`: Directorio con programas de prueba.
   - `test/fail`: Programas que deben fallar. La subcarpeta en la que se encuentran indica la etapa.
   - `test/pass`: Programas que deben compilarse correctamente.