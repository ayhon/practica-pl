package ditto.ast.literals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ditto.ast.Identifier;
import ditto.ast.CompilationProgress;
import ditto.ast.Context;
import ditto.ast.Delta;
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
    private int position;

    public StructLiteral(Identifier iden, Map<String, Expr> fieldValues) {
        this.iden = iden;
        this.fieldValues = fieldValues;
    }

    public Identifier getIden() {
        return iden;
    }

    @Override
    public String getAstString() {
        String output = "StructLiteral [ " + iden + " ]";

        if (this.getProgress().atLeast(CompilationProgress.FUNC_SIZE_AND_DELTAS))
            output += String.format(" [delta = %d] ", this.position);

        return output;
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
    public void computeOffset(Delta lastDelta) {
        /// Necesito reservar espacio de 1 int para guardar la dirección de inicio del
        /// StructLiteral en heap
        /// Porque lo rellenamos primero en el heap, y luego se copia a donde
        /// tiene que copiar
        this.position = lastDelta.useNextOffset(4);

        super.computeOffset(lastDelta);
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        out.comment("Evaluando struct literal: " + this.decompile());

        /// Reservar espacio en heap
        out.i32_const(this.type().size());
        out.call(ProgramOutput.RESERVE_HEAP);

        /// Guardar dirección del inicio del array
        out.mem_local(this.position);
        out.get_global("NP");
        out.i32_store();

        out.indented(() -> {
            /// Evaluar cada elemento (puede contener también tipos no básicos)
            var attributes = this.definition.getAttributes();

            for (var entry : fieldValues.entrySet()) {
                var field = attributes.get(entry.getKey());
                var expr = entry.getValue();

                out.comment("Guardando campo " + field.getIden() + " del StructLiteral con offset " + field.getDelta());
                out.comment("Evaluando campo " + field.getIden());

                if (field.type().isBasic) {
                    out.comment("Es un tipo básico, copiar con i32_store");
                    out.mem_local(this.position);
                    out.i32_load();
                    out.i32_const(field.getOffset());
                    out.i32_add();

                    expr.compileAsExpr(out);

                    out.i32_store();
                } else {
                    /// Caso recursivo, copiar con ncopy
                    out.comment("Es un tipo no basico, hay que evaluarlo primero");

                    out.comment("FROM");
                    out.indented(() -> {
                        expr.compileAsExpr(out);
                    });

                    out.comment("TO");
                    out.mem_local(this.position);
                    out.i32_load();
                    out.i32_const(field.getOffset());
                    out.i32_add();

                    out.comment("SIZE");
                    out.i32_const(expr.type().size() / 4);

                    out.call(ProgramOutput.COPYN);
                }
            }
        });

        /// Devolver dirección del inicio del StructLiteral
        out.mem_local(this.position);
        out.i32_load();
    }
}