package ditto.ast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ditto.ast.definitions.DefFunc;
import ditto.ast.definitions.DefVar;
import ditto.ast.definitions.Definition;
import ditto.errors.SemanticError;

public class LocalContext {

    private List<LocalScope> scopes;

    private class LocalScope {
        private Map<String, DefVar> vars;
        // Vamos a añadir atributos para poder tener deltas

        public LocalScope() {
            vars = new HashMap<>();
        }

        public void addDef(DefVar var) {
            vars.put(var.getIden(), var);
        }

        public DefVar getDef(String iden) {
            return vars.get(iden);
        }

    }

    public LocalContext() {
        scopes = Arrays.asList(new LocalScope());
    }
    public LocalContext(DefFunc func) {
        LocalScope scope = new LocalScope();
        for(DefFunc.Param param : func.getParams()) {
            // De alguna manera convendría indicar que son parámetros
            // Funciona sino cuando tenemos parámetros pasados por referencia?
            scope.addDef(new DefVar(param.getType(), param.getName()));
        }
        scopes = Arrays.asList(scope);
    }

    public void pushLightScope() {
        scopes.add(new LocalScope());
    }

    public void popLightScope() {
        assert (scopes.size() > 1);
        scopes.remove(scopes.size() - 1);
    }

    public void addDef(DefVar var) {
        scopes.get(scopes.size() - 1).addDef(var);
    }

    public DefVar getDef(String iden) {
        DefVar def = null;
        for (int i = scopes.size() - 1; i >= 0 && def == null; i--) {
            def = scopes.get(i).getDef(iden);
        }
        return def;
    }

    /**
     * Gets the local definition of a variable, or the global one if it's not
     * defined in the local context.
     * <p>
     * <b>Cannot return null</b>, since it will throw an error if the variable is
     * not defined.
     * 
     * @param iden
     * @param globalScope
     * @throws SemanticError
     * @return The definition of the variable in the local or global context.
     */
    public Definition getDefOrGlobal(String iden, Module globalScope) {
        Definition def = getDef(iden);
        if (def == null)  // Couldn't find in local context, try global
            def = globalScope.getDefinition(iden);
           // TODO: Comprobar que no es una función y una variable a la vez
        if (def == null) // Couldn't find in global context, throw error since it wasn't defined
            throw new SemanticError("Can't access undefined variable '" + iden + "'.");
        return def;
    }
}
