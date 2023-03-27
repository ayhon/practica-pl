package ditto.ast;

public class OperBin extends Oper {
    public enum Operators {
        SUM,
        SUBS,
        MUL,
        DIV,
        MODULO,
        EQUALS,
        NOTEQUALS,
        LESS,
        GREATER,
        LESS_EQUAL,
        GREATER_EQUAL,
        AND,
        OR,
        ACCESS,
        ACCESS_ARR
    }

    private final Operators op;
    private final Expr left;
    private final Expr right;

    OperBin(Operators op, Expr left, Expr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }

    public Operators getOp() {
        return op;
    }

    @Override
    public String toString() {
        return "(" + op + " " + left + " " + right + ")";
    }
}
