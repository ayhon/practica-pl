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

public class Var extends Designator {
    public final String iden;
    public final List<String> module; // If it's empty, it's defined in the current module
    public Definition definition = null;

    public Var(String iden, List<String> module) {
        this.iden = iden;
        this.module = module;
    }

    public Var(List<String> name) {
        this(name.get(name.size() - 1), name.subList(0, name.size() - 1));
    }

    public Var(String iden) {
        this(iden, new ArrayList<>());
    }

    public String getIden() {
        return iden;
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
        if (module.isEmpty()) {
            definition = localContext.getDefOrGlobal(iden, globalScope);
        } else {
            definition = globalScope.getImportedGlobal(module, iden);
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

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }
}
