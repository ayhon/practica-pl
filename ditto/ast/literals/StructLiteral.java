package ditto.ast.literals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ditto.ast.Identifier;
import ditto.ast.Context;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefStruct;
import ditto.ast.definitions.DefVar;
import ditto.ast.expressions.Expr;
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

    public Identifier getIden() {
        return iden;
    }

    @Override
    public String getAstString() {
        return "StructLiteral [ " + iden + " ]";
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

    @Override
    public void compileAsExpr(ProgramOutput out) {
        throw new RuntimeException("No debes llamar a compileAsExpr de un StructLiteral");
    }

    @Override
    public void compileAsAssign(ProgramOutput out) {
        /// WARNING: Aqui se espera que le pase una dirección de memoria de inicio donde
        /// hacer la asignación del array
        out.comment("Asignando struct literal: " + this.decompile());

        out.indented(() -> {
            /// Evaluar cada elemento (puede contener también tipos no básicos)
            /// Itero por attributes de definicion, para sacar valores por defecto
            var attributes = this.definition.getAttributes();

            for (var attr : attributes.entrySet()) {
                var field = attr.getKey();
                var def = attr.getValue();
                var expr = fieldValues.getOrDefault(field, def.getExpr());

                out.comment("Guardando campo " + field + " del StructLiteral con offset " + def.getDelta());
                out.comment("Evaluando campo " + field);

                out.duplicate();
                out.i32_const(def.getOffset());
                out.i32_add();

                if (expr == null) {
                    /// Rellenar con 0
                    out.i32_const(def.type().size() / 4);
                    out.call(ProgramOutput.FILL_ZERO);
                } else if (def.type().isBasic) {
                    out.comment("Es un tipo básico, copiar con i32_store");
                    expr.compileAsAssign(out);
                } else {
                    expr.compileAsAssign(out);
                }
            }

            if (!attributes.isEmpty())
                out.drop(); /// Quitar direccion inicial
        });
    }
}