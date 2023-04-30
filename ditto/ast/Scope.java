package ditto.ast;

import java.util.HashMap;
import java.util.Map;

import ditto.ast.definitions.Definition;

public class Scope {
    private final Map<String, Definition> defs = new HashMap<>();

    public void add(Definition def) {
        defs.put(def.getIden(), def);
    }

    public Definition get(String iden) {
        return defs.get(iden);
    }

    public boolean contains(String iden) {
        return defs.containsKey(iden);
    }
}
