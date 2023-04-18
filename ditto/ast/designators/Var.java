package ditto.ast.designators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.GlobalScope;
import ditto.ast.LocalContext;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefVar;
import ditto.ast.types.Type;

public class Var extends Designator {
    public final String iden;
    public final List<String> module; // If it's empty, it's defined in the current module
    public DefVar definition = null;
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

    @Override
    public void bind(GlobalScope globalScope, LocalContext localContext) {
        definition = localContext.getDefOrGlobal(iden, globalScope);
    }

    @Override
    public Type type() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'type'");
    }

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }
}
