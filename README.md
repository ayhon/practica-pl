# Práctica PL

Implementación de un compilador de `ditto` a WASM. Práctica de la asignatura *Procesadores de Lenguaje*

## Entregas
 - [> Feb] Primera entrega  
   Descripción propuesta del lenguaje
 - [> Mar] Segunda entrega  
   Análisis léxico y sintáctico, con mínima recuperación de errores y generación del AST
 - [= May] Tercera entrega  
   Análisis semántico (vinculación y tipos) y generación del WebAssembly

## Uso

Compilar lexer y parser:

```bash
./run.sh build all
```

Mostrar AST de un fichero `testFile.ditto` situado en `test/`:

```bash
./run.sh ast testFile
```

Mostrar AST de todos los ficheros `*.ditto` situados en `test/`:

```bash
./run.sh test-ast
```