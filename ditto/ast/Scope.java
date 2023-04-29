package ditto.ast;

import java.util.HashMap;
import java.util.Map;

import ditto.ast.definitions.DefFunc;
import ditto.ast.definitions.DefVar;
import ditto.ast.definitions.Definition;

public class Scope {
    private final Map<String, Definition> defs = new HashMap<>();

    public Scope() {
    }

    public Scope(DefFunc func) {
        for (DefFunc.Param param : func.getParams()) {
            add(param);
        }
    }

    public void add(Definition def) {
        defs.put(def.getIden(), def);
    }

    public Definition get(String iden) {
        return defs.get(iden);
    }
}
