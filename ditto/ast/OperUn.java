package ditto.ast;

public class OperUn extends Oper {
    public enum Operators {
        NOT, NEG, POS, REF, DEREF;
    }

    private final Operators op;
    private final Expr expr;

    OperUn(Operators op, Expr expr) {
        this.op = op;
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }

    public Operators getOp() {
        return op;
    }

    @Override
    public String toString() {
        return "(" + op + " " + expr + ")";
    }
}
