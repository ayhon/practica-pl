package ditto.ast.statements;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import ditto.ast.Module;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;

public class While extends Statement {
    public final Expr cond;
    public final List<Statement> statements;

    public While(Expr cond, List<Statement> statements) {
        this.cond = cond;
        this.statements = statements;
    }

    @Override
    public String getAstString() { return "while"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(cond, statements); }

    @Override
    public void bind(Module global, LocalContext local) {
        local.pushLightScope();
        super.bind(global, local);
        local.popLightScope();
    }

    @Override
    public Type type() {
        cond.type();

        return null;
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(cond);
        children.addAll(statements);
        return children;
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        out.block(null);
    }
}
