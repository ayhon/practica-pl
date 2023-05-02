package ditto.ast.statements;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import ditto.ast.Context;
import ditto.ast.Delta;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefVar;
import ditto.ast.expressions.Expr;
import ditto.ast.literals.Natural;
import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;

public class For extends Statement {
    public final DefVar index;
    public final Expr from;
    public final Expr to;
    public final Expr by;
    public final List<Statement> body;

    public For(String index, Expr from, Expr to, Expr by, List<Statement> body) {
        this.index = new DefVar(IntegerType.getInstance(), index);
        this.from = from;
        this.to = to;
        this.by = by;
        this.body = body;
    }

    public For(String index, Expr from, Expr to, List<Statement> body) {
        this(index, from, to, new Natural(1), body);
    }

    @Override
    public String getAstString() {
        return "for";
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(index);
        children.add(from);
        children.add(to);
        children.add(by);
        children.addAll(body);
        return children;
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(index, from, to, by, body);
    }

    @Override
    public void bind(Context ctx) {
        ctx.pushScope();
        super.bind(ctx);
        ctx.popScope();
    }

    @Override
    public void typecheck() {
        super.typecheck();
        Type fromType = from.type();
        if (!fromType.equals(to.type())) {
            throw new RuntimeException("Type mismatch in for loop");
        }

        if (!fromType.equals(by.type())) {
            throw new RuntimeException("Type mismatch in for loop");
        }
    }

    @Override
    public void computeOffset(Delta lastDelta) {
        lastDelta.enterBlock();
        super.computeOffset(lastDelta);
        lastDelta.exitBlock();
    }

    @Override
    public String toString() {
        return "For(" + index.decompile() + " = " + from.decompile() + " to " + to.decompile() + " by " + by.decompile() + ")";
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        out.comment("INSTRUCTION: " + this.decompile());
        int step  = by.evalIntAtCompileTime();

        initializeIndex(out);
        out.block_loop(() -> {
            checkCondition(out, step);
            for (Statement s : body) {
                s.compileAsInstruction(out);
            }
            increaseIndex(out, step);
            out.br(0);
        });
    }

    private void initializeIndex(ProgramOutput out){
        // Iniciar índice //
        // Cargar posición índice
        out.mem_location(index);
        // Cargar valor inicial
        from.compileAsExpr(out);
        // Guardar valor inicial en índice
        out.i32_store();
    }

    private void increaseIndex(ProgramOutput out, int step) {
        // Incrementar índice //
        // Cargar índice
        out.mem_location(index);
        out.duplicate(); // Una posición para guardar y otra para cargar
        // Cargar valor actual del índice
        out.i32_load();
        // Incrementar valor actual
        out.i32_const(step);
        out.i32_add();
        // Guardar valor actual en índice
        out.i32_store();
    }   

    private void checkCondition(ProgramOutput out, int step) {
        //Caragar direccion indice
        out.mem_location(index);
        // Cargar valor actual del índice
        out.i32_load();
        // Cargar valor final
        to.compileAsExpr(out);
        // out.i32_const(stop);
        if(step > 0){
            out.i32_le_s();
        }else{
            out.i32_ge_s();
        }
        out.br_if(1);
    }
}
