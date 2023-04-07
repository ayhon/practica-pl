package ditto.ast.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.expressions.Expr;

public class While extends Node implements Statement {
    public final Expr cond;
    public final ArrayList<Statement> statements;

    public While(Expr cond, ArrayList<Statement> statements) {
        this.cond = cond;
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "(while " + cond + " " + statements + ")";
    }

    @Override
    public String getAstString() { return "while"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(cond, statements); }
}
