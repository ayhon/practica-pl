package ditto.ast.designators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.GlobalContext;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.Definition;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class Var extends Designator {
    private final String iden;
    private final String module;
    private Definition definition = null;

    public Var(String iden, String module) {
        this.iden = iden;
        this.module = module;
    }

    public Var(String iden) {
        this(iden, null);
    }

    public String getIden() {
        return iden;
    }

    public String getModule() {
        return module;
    }

    public boolean hasModule() {
        return module != null;
    }

    @Override
    public String getAstString() {
        return "var";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(iden);
    }

    @Override
    public void bind(GlobalContext globalScope, LocalContext localContext) {
        if (module == null) {
            definition = localContext.getDefOrGlobal(iden, globalScope);
        } else {
            definition = globalScope.getImportedGlobal(module, iden);
        }

        if (definition == null) {
            throw new TypeError("Could not find definition for " + iden);
        }
    }

    @Override
    public Type type() {
        return definition.type();
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList();
    }

    @Override
    public void compileAsDesig(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsDesig'");
    }

    public Definition getDefinition() {
        return definition;
    }
}