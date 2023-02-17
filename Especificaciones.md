# Especificación del lenguaje

- Identificadores y ámbitos de definición (scopes)
  - Variables simples
  - Arrays
  - Bloques anidados
  - Funciones (paso parámetros por valor o referencia)
  - Punteros
  - Registros (?)
  - Clases (sin herencia, más como structs)
  - Módulos
  - Cláusulas de importanción
- Tipos
  - Declaración explícita del tipo de variable
  - Tipos básicos predefinidos (enteros, booleanos)
  - Operadores infijos con prioridad y asociatividad para distintos tipos
  - Tipo array
  - Comprobación de tipos
  - Definición de tipos de usuario
- Instrucciones del lenguaje
  - Asignación (incluyendo elementos de arrays)
  - Condicional con 1 y 2 ramas (if y if-else)
  - Bucle
  - Expresiones formadas por constantes, e identificadores con o sin subíndices (a o a[1])
  - Operadoes infijos
  - Llamadas a función
  - Instruciones de entrada y salida
  * Expresiones con punteros y nombres cualificados (notacion `.`)
  * Instrucción case (Salto a cada rama en tiempo constante)
  * Métodos de la clase
  * Instrucciones de reserva de memoria dinámica
- Gestión de errores
  * Indicación del tipo de error, fila y columna
  * Parar la compilación
  * Recuperación de errores