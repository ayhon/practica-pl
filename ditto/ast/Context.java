package ditto.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.definitions.DefFunc;
import ditto.ast.definitions.Definition;

public class Context {
    private final List<Scope> scopes;
    private final Module module;

    public Context(Module module, Scope global) {
        this.module = module;
        scopes = new ArrayList<>(Arrays.asList(global));
    }

    public Module getModule() {
        return module;
    }

    public void pushScope() {
        scopes.add(new Scope());
    }

    public void pushScope(DefFunc func) {
        assert(scopes.size() == 1);
        scopes.add(new Scope(func));
    }

    public void popScope() {
        assert (scopes.size() > 1);
        scopes.remove(scopes.size() - 1);
    }

    public void add(Definition var) {
        scopes.get(scopes.size() - 1).add(var);
    }

    public Definition get(String iden) {
        Definition def = null;
        for (int i = scopes.size() - 1; i >= 0 && def == null; i--) {
            def = scopes.get(i).get(iden);
        }
        return def;
    }

    public Definition get(Identifier iden) {
        if (iden.hasModule()) {
            return module.getDefinition(iden);
        } else {
            return get(iden.getName());
        }
    }
}
