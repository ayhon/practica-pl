package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.Type;

public class OperBin extends Node implements Expr {
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
        ACCESS_ARR;

        public String toString() {
            switch (this) {
                case SUM:
                    return "+";
                case SUBS:
                    return "-";
                case MUL:
                    return "*";
                case DIV:
                    return "/";
                case MODULO:
                    return "%";
                case EQUALS:
                    return "==";
                case NOTEQUALS:
                    return "!=";
                case LESS:
                    return "<";
                case GREATER:
                    return ">";
                case LESS_EQUAL:
                    return "<=";
                case GREATER_EQUAL:
                    return ">=";
                case AND:
                    return "and";
                case OR:
                    return "or";
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }
        }

        public boolean isDesignator() {
            switch (this) {
                case ACCESS:
                    return true;
                case ACCESS_ARR:
                    return true;
                default:
                    return false;
            }
        }
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
    public Type getType() {
        /// El tipo resultante será el tipo de ambas expresiones
        /// (tiene que ser el mismo)
        if (left.getType() != right.getType()) {
            throw new IllegalArgumentException("OperBin: left and right types must be the same");
        }

        return left.getType();
    }

    @Override
    public String getAstString() {
        return op.toString();
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(left, right);
    }
}
