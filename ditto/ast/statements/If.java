package ditto.ast.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.CompilationProgress;
import ditto.ast.Context;
import ditto.ast.Delta;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.BoolType;
import ditto.errors.TypeError;

public class If extends Statement {
    private Expr cond;
    private List<Statement> then;
    private List<Statement> els;

    public If(Expr cond, List<Statement> then) {
        this.cond = cond;
        this.then = then;
        this.els = new ArrayList<Statement>();
    }

    public If(Expr cond, List<Statement> then, List<Statement> els) {
        this.cond = cond;
        this.then = then;
        this.els = els;
    }

    @Override
    public String getAstString() {
        return "if";
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(cond);
        children.addAll(then);
        children.addAll(els);
        return children;
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(cond, then, els);
    }

    @Override
    public void bind(Context ctx) {
        super.setProgress(CompilationProgress.BIND);
        cond.bind(ctx);

        ctx.pushScope();
        for (Statement s : then) {
            if(s.getProgress().lessThan(CompilationProgress.BIND))
                s.bind(ctx);
        }
        ctx.popScope();

        /// El scope de else es diferente al de condición principal
        ctx.pushScope();
        for (Statement s : els) {
            if(s.getProgress().lessThan(CompilationProgress.BIND))
                s.bind(ctx);
        }
        ctx.popScope();
    }

    @Override
    public void typecheck() {
        super.typecheck();
        if (!cond.type().equals(BoolType.getInstance())) {
            throw new TypeError(
                    String.format("Condition of if statement must be of type bool, got %s in %s", cond.type(), cond));
        }
    }

    @Override
    public void computeOffset(Delta delta) {
        super.setProgress(CompilationProgress.FUNC_SIZE_AND_DELTAS);
        
        delta.enterBlock();
        for (Statement s : then) {
            s.computeOffset(delta);
        }
        delta.exitBlock();

        delta.enterBlock();
        for (Statement s : els) {
            s.computeOffset(delta);
        }
        delta.exitBlock();

        /**
         * nextOffset offsetSize
         * 0 0
         * int a; delta=0 1 1
         * if cond then (1) 1 1
         * int b; delta=1 2 2
         * if cond2 then (2) 2 2
         * int c; delta=2 3 3
         * end 2 3
         * int d; delta=2 3 3
         * end
         */
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        cond.compileAsExpr(out);
        out._if();
        for (Statement s : then) {
            s.compileAsInstruction(out);
        }
        out._else();
        for (Statement s : els) {
            s.compileAsInstruction(out);
        }
        out._end();
    }
}
