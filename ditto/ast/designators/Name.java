package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Identifier;
import ditto.ast.Context;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefVar;
import ditto.ast.definitions.Definition;
import ditto.errors.BindingError;
import ditto.errors.SemanticError;

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
    public List<Node> getAstChildren() {
        return Arrays.asList();
    }

    public Definition getDefinition() {
        return definition;
    }

    @Override
    public void bind(Context ctx) {
        super.bind(ctx);

        definition = ctx.get(this.iden);
        if (definition == null) {
            throw new BindingError(String.format("'%s' is not defined", iden));
        }

        this.type = definition.type();
    }

    @Override
    public void compileAsDesig(ProgramOutput out) {
        if (definition instanceof DefVar) {
            DefVar defVar = (DefVar) definition;
            if (defVar.isGlobal()) {
                out.i32_const(defVar.getOffset());
            } else {
                out.get_local(ProgramOutput.LOCAL_START);
                out.i32_const(defVar.getOffset());
                out.i32_add();
            }
        } else
            throw new SemanticError("Can't compile a definition to " + definition + " from a name");
    }
}