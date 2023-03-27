package ditto.ast;

public class Assign implements Node {
    private final String id;
    private final Expr expr;

    Assign(String id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "(assign " + id + " " + expr + ")";
    }
}
