package ditto.ast;

import java.util.HashMap;
import java.util.Map;

import ditto.ast.definitions.DefFunc;
import ditto.ast.definitions.DefVar;
import ditto.ast.definitions.Definition;

public class Scope {
    private Map<String, Definition> defs;

    public Scope() {
        defs = new HashMap<>();
    }

    public Scope(DefFunc func) {
        this();
        for (DefFunc.Param param : func.getParams()) {
            // De alguna manera convendría indicar que son parámetros
            // Funciona sino cuando tenemos parámetros pasados por referencia?
            add(new DefVar(param.getType(), param.getName()));
        }
    }

    public void add(Definition def) {
        defs.put(def.getIden(), def);
    }

    public Definition get(String iden) {
        return defs.get(iden);
    }
}
