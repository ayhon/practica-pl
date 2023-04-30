package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefVar;
import ditto.ast.designators.Name;
import ditto.ast.types.BoolType;
import ditto.ast.types.IntegerType;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class OperUn extends Expr {
    public enum Operators {
        NOT, NEG, POS, REF;

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
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }
        }
    }

    private final Operators op;
    private final Expr expr;

    public OperUn(Operators op, Expr expr) {
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
    public String getAstString() {
        return op.toString();
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(expr);
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(expr);
    }

    @Override
    public Type type() {
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
                return new PointerType(expr.type());
            default:
                throw new IllegalArgumentException("Invalid operator '" + op + "'");
        }
    }

    @Override
    public void typecheck() {
        super.typecheck();
        Type expectedType = null;
        switch (this.op) {
            case NOT -> {
                expectedType = BoolType.getInstance();
            }
            case NEG, POS -> {
                expectedType = IntegerType.getInstance();
            }
            case REF -> {
                /// Porque cualquier tipo puede ser referenciado
                expectedType = null;
            }
            default -> {
                throw new IllegalArgumentException("Invalid operator '" + op + "'");
            }
        }

        if (expectedType != null && expectedType.getClass() != this.expr.type().getClass()) {
            throw new TypeError(String.format("No se puede aplicar el operador '%s' a una expresión de tipo '%s'",
                    this.op, this.expr.type()));
        }
    }
        @Override
    public void compileAsExpr(ProgramOutput out) {
        switch (this.op) {
            case NOT:
                // Se da con expresion `not x`,
                out.i32_const(1);
                expr.compileAsExpr(out);
                out.i32_xor();
                break;
            case NEG:
                // Se da con expresion `-x`,
                expr.compileAsExpr(out);
                out.i32_const(0);
                out.i32_sub();
                break;
            case POS:
                // Se da con expresion `+x`,
                expr.compileAsExpr(out);
                break;
            case REF:
                // Se da con expresion `ptr x`,
                expr.compile(out);
                out.i32_const(((DefVar) (((Name) expr).getDefinition())).getDelta()); // Aqui hacemos i32.const delta(*id)
                                                                                        // (TODO: revisar)
                out.i32_add();
                break;
            default:
                throw new IllegalArgumentException("Invalid operator '" + op + "'");
        }
    }

}
