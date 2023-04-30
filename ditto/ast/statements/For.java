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
        ctx.add(index); // Add the for-loop's index to the new local scope
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
    public void compileAsInstruction(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }
}
