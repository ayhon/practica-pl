package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.ArrayType;
import ditto.ast.types.BoolType;
import ditto.errors.TypeError;
import ditto.ast.types.IntegerType;

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
        OR;

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
    public String getAstString() {
        return op.toString();
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(left, right);
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(left, right);
    }

    @Override
    public void typecheck() {
        super.typecheck();
        /// El tipo resultante será el tipo de ambas expresiones
        /// (tiene que ser el mismo)
        if (!left.type().equals(right.type())) {
            throw new TypeError(String.format(
                    "El tipo de la expresión izquierda (%s - %s) no coincide con el tipo de la expresión derecha (%s - %s)",
                    left, left.type(), right, right.type()));
        }

        switch (this.op) {
            case SUM, SUBS, MUL, DIV, MODULO -> {
                if (!left.type().equals(IntegerType.getInstance()))
                    throw new TypeError("Can't add a " + left.type());
                this.type = IntegerType.getInstance();
            }
            case EQUALS, NOTEQUALS -> {
                if(!left.type().equals(right.type()))
                    throw new TypeError("Can't compare different types");
                if(left.type().equals(IntegerType.getInstance()) || left.type().equals(BoolType.getInstance()))
                    this.type = BoolType.getInstance();
                else 
                    throw new TypeError("Can't compare " + left.type());
            }
            case LESS, GREATER, LESS_EQUAL, GREATER_EQUAL -> {
                if (!left.type().equals(IntegerType.getInstance()))
                    throw new TypeError("Can't compare non-Integers");
                this.type = BoolType.getInstance();
            }
            case AND, OR -> {
                if (!left.type().equals(BoolType.getInstance()))
                    throw new TypeError("Can't apply logic operators on " + left.type());
                this.type = BoolType.getInstance();
            }
            default -> {
                throw new RuntimeException("Operador no soportada: " + op.toString());
            }
        }
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        left.compileAsExpr(out);
        right.compileAsExpr(out);
        switch (op) {
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

    public String decompile() {
        return String.format("(%s %s %s)", left.decompile(), op.toString(), right.decompile());
    }

    @Override
    public Integer evalIntAtCompileTime() {
        Integer left = this.left.evalIntAtCompileTime();
        Integer rigth = this.right.evalIntAtCompileTime();

        if (rigth == null || left == null) {
            return null;
        }

        switch (op) {
            case SUM -> {
                return left + rigth;
            }
            case SUBS -> {
                return left - rigth;
            }
            case MUL -> {
                return left * rigth;
            }
            case DIV -> {
                return left / rigth;
            }
            case MODULO -> {
                return left % rigth;
            }
            default -> {
                return null;
            }
        }
    }
}
