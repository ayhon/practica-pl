package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.CompilationProgress;
import ditto.ast.Context;
import ditto.ast.Delta;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class DefVar extends Definition {
    private Expr expr;
    private int position;
    private boolean isGlobal;
    private int deepness;

    public DefVar(Type type, String iden, Expr expr) {
        // Argumentos en este orden para representar como se escribe en el lenguaje
        super(iden);
        this.type = type;
        this.expr = expr;
    }

    public DefVar(Type type, String name) {
        this(type, name, null);
    }

    @Override
    public String getAstString() {
        String output = "var";

        if (this.getProgress().atLeast(CompilationProgress.FUNC_SIZE_AND_DELTAS))
            output += String.format(" [delta = %d] [size = %d]", this.getDelta(), this.type().size());

        return output;
    }

    public int getDelta() {
        return this.position;
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(this.type);
        if (this.expr != null)
            children.add(this.expr);
        return children;
    }

    @Override
    public List<Object> getAstArguments() {
        List<Object> args = new ArrayList<>();
        args.add(type);
        args.add(this.getIden());
        if (this.expr != null)
            args.add(expr);
        return args;
    }

    public Type getType() {
        return type;
    }

    public Expr getExpr() {
        return expr;
    }

    @Override
    public void bind(Context ctx) {
        super.bind(ctx);
        ctx.add(this);
        isGlobal = ctx.isGlobal(this.getIden());
    }

    @Override
    public void typecheck() {
        super.typecheck();
        if (this.expr != null && !this.expr.type().equals(type))
            throw new TypeError(
                    String.format("Can't assign %s to variable <%s> of type %s", expr.type(), this.getIden(), type));
        else if (expr != null)
            this.type = expr.type();
        // Así adivinamos la longitud del array, para)
        // casos como este:
        // array int a = [10; 1];
    }

    @Override
    public void computeOffset(Delta delta) {
        position = delta.useNextOffset(type.size());
        super.computeOffset(delta);
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        // Es como una asignación de valores por defecto
        out.comment("INSTRUCCION: " + decompile());

        if (this.expr == null) {
            /// Rellena con ceros
            out.comment("No tiene valor por defecto, rellenar con ceros");

            out.mem_location(this);
            out.i32_const(this.type.size() / 4);
            out.call(ProgramOutput.FILL_ZERO);
        } else if (expr.type().isBasic) {
            out.comment("Asignado un tipo básico: " + expr.decompile());

            out.mem_location(this);
            expr.compileAsExpr(out);
            out.i32_store();
        } else {
            out.comment("Asignando un tipo no básico: " + expr.decompile());

            out.comment("FROM");
            out.indented(() -> {
                expr.compileAsExpr(out);
            });

            out.comment("TO");
            out.mem_location(this);

            out.comment("SIZE");
            out.i32_const(expr.type().size() / 4);

            out.call(ProgramOutput.COPYN);
        }
    }

    @Override
    public String decompile() {
        var output = "[delta=" + getOffset() + "]";
        if (expr != null)
            output += " := " + expr.decompile();
        return output;
    }

    public int getOffset() {
        return this.position;
    }

    public boolean isGlobal() {
        return isGlobal;
    }
}
