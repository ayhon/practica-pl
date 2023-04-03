package ditto.ast;

import java.util.ArrayList;

import ditto.ast.expressions.Expr;

public class While implements Statement {
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
}
