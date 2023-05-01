package ditto.ast.statements;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import ditto.ast.Context;
import ditto.ast.Delta;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.BoolType;
import ditto.errors.TypeError;

public class While extends Statement {
    public final Expr cond;
    public final List<Statement> statements;

    public While(Expr cond, List<Statement> statements) {
        this.cond = cond;
        this.statements = statements;
    }

    @Override
    public String getAstString() {
        return "while";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(cond, statements);
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(cond);
        children.addAll(statements);
        return children;
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
        if (!cond.type().equals(BoolType.getInstance())) {
            throw new TypeError(String.format("While condition must be of type Bool, got %s in %s", cond.type(), cond));
        }
    }

    @Override
    public void computeOffset(Delta lastDelta) {
        lastDelta.enterBlock();
        super.computeOffset(lastDelta);
        lastDelta.exitBlock();
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        out.block_loop(() -> {
            this.cond.compileAsExpr(out);   // Cargamos en la cima de la pila la expresion
            out.i32_eq_z();                 // if cond == 0 (true)
            out.br_if(1);
            for (Statement s : statements) {// Compilamos las intrucciones    
                s.compileAsInstruction(out);
            }
            out.br(0);
        });
    }
}
