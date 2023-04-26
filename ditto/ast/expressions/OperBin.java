package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.Type;

public class OperBin extends Expr {
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
    }

    private final Operators op;
    private final Expr left;
    private final Expr right;

    public OperBin(Operators op, Expr left, Expr right) {
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
    public Type type() {
        /// El tipo resultante será el tipo de ambas expresiones
        /// (tiene que ser el mismo)
        if (left.type() != right.type()) {
            throw new IllegalArgumentException("OperBin: left and right types must be the same");
        }

        return left.type();
    }

    @Override
    public String getAstString() {
        return op.toString();
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(left, right);
    }
    
    @Override
    public void compileAsExpr(ProgramOutput out) {
        left.compileAsExpr(out);
        right.compileAsExpr(out);
        switch(op){
            case SUM:
                out.i32_add();
                break;
            case SUBS:
                out.i32_sub();
                break;
            case MUL:
                out.i32_mul();
                break;
            case DIV:
                out.i32_div_s();
                break;
            case MODULO:
                out.i32_rem_s();
                break;
            case EQUALS:
                out.i32_eq();
                break;
            case NOTEQUALS:
                out.i32_ne();
                break;
            case LESS_EQUAL:
                out.i32_le_s();
                break;
            case LESS:
                out.i32_lt_s();
                break;
            case GREATER_EQUAL:
                out.i32_ge_s();
                break;
            case GREATER:
                out.i32_gt_s();
                break;
            case AND:
                out.i32_and();
                break;
            case OR:
                out.i32_or();
                break;
            default:
                throw new RuntimeException("Operador no soportada: " + op.toString());
        }
    }
    
    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(left, right);
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }
}
