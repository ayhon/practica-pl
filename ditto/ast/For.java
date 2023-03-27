package ditto.ast;

import java.util.ArrayList;

public class For implements Node {
    private final String index;
    private final Expr from;
    private final Expr to;
    private final Expr by;
    private final ArrayList<Statement> body;

    For(String index, Expr from, Expr to, Expr by, ArrayList<Statement> body) {
        this.index = index;
        this.from = from;
        this.to = to;
        this.by = by;
        this.body = body;
    }

    @Override
    public String toString() {
        return "(for " + index + " " + from + " " + to + " " + by + " " + body + ")";
    }
}
