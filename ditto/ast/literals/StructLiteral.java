package ditto.ast.literals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ditto.ast.Identifier;
import ditto.ast.Context;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefStruct;
import ditto.ast.definitions.DefVar;
import ditto.ast.expressions.Expr;
import ditto.ast.types.StructType;
import ditto.errors.BindingError;
import ditto.errors.TypeError;

public class StructLiteral extends Literal {
    private final Identifier iden;
    private final Map<String, Expr> fieldValues;
    private DefStruct definition;

    public StructLiteral(Identifier iden, Map<String, Expr> fieldValues) {
        this.iden = iden;
        this.fieldValues = fieldValues;
    }

    public StructLiteral(StructType type) {
        this.iden = type.getIden();
        this.fieldValues = new HashMap<>();
        for (String fieldName : type.getFieldTypes().keySet()) {
            fieldValues.put(fieldName, type.getFieldTypes().get(fieldName).getDefault());
        }
        this.type = type;

        /// Porque este constructor se llama en fase de computeTypeSize, entonces ya
        /// sabemos la definicion y no hay que hacer bind
        this.definition = type.getDefinition();
    }

    public Identifier getIden() {
        return iden;
    }

    @Override
    public String getAstString() {
        return "struct";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(iden, fieldValues);
    }

    @Override
    public Object getValue() {
        return fieldValues;
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.addAll(fieldValues.values());
        return children;
    }

    @Override
    public String decompile() {
        return iden.toString() + " { " + fieldValues.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue().decompile())
                .reduce("", (a, b) -> b + ", " + a)
                + " }";
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsExpr'");
    }

    @Override
    public void bind(Context ctx) {
        var def = ctx.get(iden);
        if (def instanceof DefStruct) {
            definition = (DefStruct) def;
        } else
            throw new BindingError("Couldn't find definition for module " + iden);

        super.bind(ctx);
    }

    @Override
    public void typecheck() {
        super.typecheck();
        for (var entry : fieldValues.entrySet()) {
            DefVar field = this.definition.getAttribute(entry.getKey());
            if (field == null) {
                /// No existe este campo
                throw new TypeError(String.format("No existe campo [%s] en struct [%s]", entry.getKey(), this.iden));
            }

            if (!field.type().equals(entry.getValue().type())) {
                throw new TypeError(String.format(
                        "El tipo del campo [%s] en struct [%s] no coincide con la definicion original de [%s]",
                        entry.getKey(), this.iden, field.type()));
            }
        }
        this.type = this.definition.type();
    }
}