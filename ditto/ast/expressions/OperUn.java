package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.BoolType;
import ditto.ast.types.IntegerType;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;

public class OperUn extends Node implements Expr {
    public enum Operators {
        NOT, NEG, POS, REF, DEREF;

        public String toString() {
            switch (this) {
                case NOT:
                    return "not";
                case NEG:
                    return "-";
                case POS:
                    return "+";
                case REF:
                    return "ptr";
                case DEREF:
                    return "@";
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }
        }
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
    public Type getType() {
        switch (this.op) {
            case NOT:
                return BoolType.getInstance();
            case NEG:
                return IntegerType.getInstance();
            case POS:
                return IntegerType.getInstance();
            case REF:
                /// Se da con expresion `ptr x`,
                /// que sirve para obtener el puntero a la variable x
                return new PointerType(expr.getType());
            case DEREF: 
                /// Se da con expresion `@x`,
                /// que sirve para obtener el valor apuntado por el puntero x
                return ((PointerType) expr.getType()).getElementType();
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    @Override
    public String getAstString() {
        return op.toString();
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(expr);
    }
}
