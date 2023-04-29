package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.GlobalContext;
import ditto.ast.Identifier;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.Definition;
import ditto.ast.types.Type;

// A name designates a variable or a function.
public class Name extends Designator {
    private final Identifier iden;
    private Definition definition = null;

    public Name(Identifier iden) {
        this.iden = iden;
    }

    public Identifier getIden() {
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
        if (this.iden.hasModule()) {
            definition = globalScope.getImportedDef(iden.getModule(), iden.getName());
        } else {
            definition = localContext.getDefOrGlobal(iden.getName(), globalScope);
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