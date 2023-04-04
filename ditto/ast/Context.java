package ditto.ast;

import java.util.HashMap;
import java.util.Map;

import ditto.ast.types.Type;

public class Context {
    /// Clase para saber en un punto del programa, qué variables están definidas
    /// Y de qué tipo son
    /// Tiene que ser inmutable para evitar problemas
    private final Map<String, Type> variables;

    public Context() {
        /// Crea un nuevo contexto
        this.variables = new HashMap<>();
    }

    private Context(Context other) {
        /// Crea un nuevo contexto a partir de otro
        this.variables = new HashMap<>(other.variables);
    }

    public Type getVariableType(String name) {
        /// Devuelve el tipo de una variable
        /// Si no está definida, devuelve null
        return variables.get(name);
    }

    public Context addVariable(String name, Type type) {
        Context newContext = new Context(this);
        newContext.variables.put(name, type);
        return newContext;
    }
}
