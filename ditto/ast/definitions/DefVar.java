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
    private String iden;
    private Expr expr;
    private int position;
    private boolean isGlobal;

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
            output += String.format(" [delta = %d]", this.getDelta());

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
            children.add(expr);
        return children;
    }

    @Override
    public List<Object> getAstArguments() {
        List<Object> args = new ArrayList<>();
        args.add(type);
        args.add(iden);
        if (this.expr != null)
            args.add(expr);
        return args;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void bind(Context ctx) {
        super.bind(ctx);
        ctx.add(this);
        isGlobal = ctx.isGlobal(this.iden);
    }

    @Override
    public void typecheck() {
        super.typecheck();
        if (expr != null && !expr.type().equals(type))
            throw new TypeError(String.format("Can't assign %s to variable %s of type %s", expr.type(), iden, type));
        else if (expr != null)
            type = expr.type();
        // Así adivinamos la longitud del array, para)
        // casos como este:
        // array int a = [10; 1];
    }

    @Override
    public Type type() {
        return getType();
    }

    @Override
    public void computeTypeSize() {
        super.computeTypeSize();
        // Tras haber calculado los tamaños de los tipos
        // ya sabemos si los tipos son representables o no
        if (expr == null) {
            this.expr = getType().getDefault();
            type = expr.type();
        }
    }

    @Override
    public void computeOffset(Delta delta) {
        super.computeOffset(delta);
        position = delta.useNextOffset(type.size());
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {

    }

    public int getOffset() {
        return this.position;
    }

    public boolean isGlobal() {
        return isGlobal;
    }
}
