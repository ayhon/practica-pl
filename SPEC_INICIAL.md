# Especifiación final

### Entrada del programa
El programa se empieza a ejecutar en la funión `main`.

### Asignación

```
int x:= 10;
```

```
ASIGNACION ::= TIPO IDEN PCOMA
          |  TIPO IDEN OP_ASIGN EXPR PCOMA 
```

### Condicional con 1 y 2 ramas
La sintaxis sobre los condicionales en nuestro leguaje serán  la de:

```
if cond then
codigo
end
```
Y en caso de dos ramas:

```
if cond then
codigo
else if cond then
codigo
end
```

## ATTENTION:?Esto habría que quitarlo no?
```
INST_IF ::= IF EXPR THEN STMTs END
       |  IF EXPR THEN STMTs ELSE STMTs END
```

### Bucles

```
while cond do
codigo
end
```

```
INST_WHILE ::= WHILE EXPR DO STMTs END
```

```
for k from 1 to N  do
for i from 1 to N do
  for j from 1 to N do
    codigo
  end
end
end
```

```
INST_FOR ::= FOR IDEN FROM EXPR TO EXPR DO STMTs END
        |  FOR IDEN FROM EXPR TO EXPR STEP EXPR DO STMTs END
```

### Operadoes infijos.

  Los mismos que C

### Llamadas a función.

 Para llevar a cabo llamadas a funciones, utilizamos el nombre de la función seguido por paréntesis que contienen los parámetros para la misma separados por comas. Ejemplo:

```
fun_function(a,b,10,1+3,4)
```

```
FUN_CALL ::= IDEN PAREN_AP EXPRs PAREN_CIER PCOMA;
```

### Instruciones de entrada y salida.

Usamos las palabras clave `print` y `scan`

`scan` y `print` son funciones estándar, que no necesitan de una detección especial.

```
print(10);
scan(x);
```

### Expresiones con punteros y nombres cualificados (notacion `.`)

El typo de un puntero tiene el formato `ptr int`.

```
PTR_TIPO ::= PTR TIPO_PRIMITIVO;
```

Para obtener un puntero de una variable, usamos la palabra clave `ptr`

```
ptr int a := ptr x; 
```

Para dereferencia un puntero, usamos el operador `@` (Pronunciado "at" en inglés)

```
@a;
```

En el caso que el puntero sea puntero a una estructura con campos, usamos el operador `->` para dereferenciar
el puntero y acceder al campo de la derecha (Como C++). 

```
a->next;
@a.next;
```

La prioridad de `->`hace que `@curr->next->next` es equivalente a `(@curr)->next->next`


### Instrucción case (Salto a cada rama en tiempo constante)

La sintaxis se compone de la palabra clave 'match' seguido de la variable a evaluar, seguido de la palabra clave 'is'. En las siguientes lineas se separan los diferentes casos con la siguiente estructura, primero la palabra clave 'case' seguido de la constante a comparar, segido de la palabra clave 'do', finalemente en las siguientes líneas aparecen las lineas del código para el caso. El último caso se puede abreviar con un otherwise en lugar del 'case' + const +  'do'. Terminamos con un 'end'. Ejemplo:

```
match a is
  case 1 do
    codigo
  case 2 do
    codigo
  otherwise
    codigo
end
```

### Instrucciones de reserva de memoria dinámica

TODO: Ver si hacemos esto, y como (Fer vota por instrucciones especiales)

```
ptr int a = allocate(byte_size);
free(a);
```

### Arrays

TODO: Preguntar sobre si arrays deben ser su propio tipo, o es mejor tratarlos como punteros
Si los tratamos como punteros, como reservamos memoria en la pila?

```
array int a = [1,2,3,4,5];
array int a = [0; 10];
int a = array 10; ¿?
func sort(array int a) -> array int
```

### Funciones

Hemos decidido pasar parámetros por valor, puesto tenemos punteros. Creemos que de esta forma queda más claro.

La sintaxis es la siguiente:

Comenzamos con la palabra clave 'func' seguido del nombre de la función con los parámentros y su tipo entre parétesis, a continuación aparece '->' + tipo que representa el tipo que devuelve la función. A continuación aparece el código de la función, terminando con un end. Ejemplo:

```
func nombre(int a, int a) -> int
 a := 10;
 halt(adsf);
 asdfa(f);
end
```

```
FUNC_DECL ::= FUNC IDEN ARGs STMTs END
         |  FUNC IDEN ARGs FLECHA TIPO STMTs END;
ARGs ::= PAREN_AP PAREN_CIERRE | PAREN_AP ARG_DECLs PAREN_CIERRE;
ARG_DECLs ::= TIPO IDEN | TIPO IDEN COMA ARG_DECLs;
```

### Clases (sin herencia, más como structs)

Nuestras clases son más bien `structs`, tipos que tienen campos en sus declaraciones, que pueden ser funciones en sí mismas

```
struct Nombre is
  int a;
  int b;
end

Nombre n{
  a = 10,
  b = 11
};

a.hello()
```

```
STRUCT_INST ::= STRUCT IDEN IS DECLs END;
DECLs ::= ASIGNACION | FUNC_DECL | DECLs | ;
```

### Módulos

Los módulos sirven para declarar código en distintos espacios de nombres

```
module Std is
  public int a;
  int secret;
  public func sort(ptr int array) -> ptr int
  end
end
```

```
MODULE_INSTR := MODULE IDEN IS DECLs END
```

### Cláusulas de importanción

El acceso a los campos de un módulo se hace con `::`, precedido por el nombre del módulo. Los módulos se importan al inicio del programa, usando la sintaxis 'import' + módulo. Ejemplo:

```
import Std;
Std::sort(a);
```

```
IMPORT_STMT ::= IMPORT IDEN;
           |  IMPORT IDEN FROM IDEN;
```

### Tipos básicos predefinidos (enteros, booleanos)

```
int
bool
```


Entrada del programa

0 Asignación
1 Condicional con 1 y 2 ramas (if y if-else)
2 Bucles
0 Operadoes infijos.
1 Llamadas a función.

2 Instruciones de entrada y salida.
0 Expresiones con punteros y nombres cualificados (notacion `.`)
1 Instrucción case (Salto a cada rama en tiempo constante)
2 Instrucciones de reserva de memoria dinámica
0 Arrays

1 Funciones (paso parámetros por valor o referencia)
2 Clases (sin herencia, más como structs)
0 Módulos
1 Cláusulas de importanción
2 Tipos básicos predefinidos (enteros, booleanos)