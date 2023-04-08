package ditto.ast.statements;

import java.util.List;
import java.util.Arrays;

import ditto.ast.Node;
import ditto.ast.expressions.Expr;

public class While extends Node implements Statement {
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
}
