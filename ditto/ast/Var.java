package ditto.ast;

public class Var implements Node {
    private final String id;
    private final Expr expr;

    Var(String id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "(Var " + id + " " + expr + ")";
    }
}
