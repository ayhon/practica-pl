package ditto.ast;

import java.util.ArrayList;

import ditto.ast.expressions.Expr;

public class If implements Statement {
    public final Expr cond;
    public final ArrayList<Statement> then;
    public final ArrayList<Statement> els;

    public If(Expr cond, ArrayList<Statement> then) {
        this(cond, then, new ArrayList<Statement>());
    }

    public If(Expr cond, ArrayList<Statement> then, ArrayList<Statement> els) {
        this.cond = cond;
        this.then = then;
        this.els = els;
    }

    @Override
    public String toString() {
        return "(if " + cond + " " + then + " " + els + ")";
    }
}
