package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Module;
import ditto.ast.Identifier;
import ditto.ast.Context;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.Definition;
import ditto.ast.types.Type;
import ditto.errors.SemanticError;
import ditto.errors.TypeError;

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
        return "name";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(iden);
    }

    @Override
    public void bind(Context ctx) {
        definition = ctx.getModule().getDefinition(this.iden);
        if (definition == null) {
            throw new SemanticError(String.format("'%s' is not defined", iden));
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