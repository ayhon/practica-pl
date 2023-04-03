package ditto.ast.statements;

import java.util.ArrayList;

import ditto.ast.expressions.Expr;

public class For implements Statement {
    public final String index;
    public final Expr from;
    public final Expr to;
    public final Expr by;
    public final ArrayList<Statement> body;

    public For(String index, Expr from, Expr to, Expr by, ArrayList<Statement> body) {
        this.index = index;
        this.from = from;
        this.to = to;
        this.by = by;
        this.body = body;
    }

    public For(String index, Expr from, Expr to, ArrayList<Statement> body) {
        this(index, from, to, null, body);
    }

    @Override
    public String toString() {
        return "(for " + index + " " + from + " " + to + " " + by + " " + body + ")";
    }
}
