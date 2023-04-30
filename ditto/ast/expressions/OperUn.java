package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefVar;
import ditto.ast.designators.Name;
import ditto.ast.types.ArrayType;
import ditto.ast.types.BoolType;
import ditto.ast.types.IntegerType;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class OperUn extends Expr {
    public enum Operators {
        NOT, NEG, POS, REF, LEN;

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
                case LEN:
                    return "length";
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

    private Type getExpectedType() {
        switch (this.op) {
            case NOT -> {
                return BoolType.getInstance();
            }
            case NEG, POS -> {
                return IntegerType.getInstance();
            }
            case REF -> {
                /// Porque cualquier tipo puede ser referenciado
                return null;
            }
            case LEN -> {
                return new ArrayType(null);
            }
            default -> {
                throw new IllegalArgumentException("Invalid operator '" + op + "'");
            }
        }
    }

    @Override
    public void typecheck() {
        super.typecheck();
        Type expectedType = getExpectedType();
        if (expectedType != null && !expectedType.equals(this.expr.type())) {
            throw new TypeError(String.format("No se puede aplicar el operador '%s' a una expresión de tipo '%s'",
                    this.op, this.expr.type()));
        }
        this.type = computeType();
    }

    private Type computeType() {
        switch (this.op) {
            case NOT:
                this.type = BoolType.getInstance();
            case NEG:
                this.type = IntegerType.getInstance();
            case POS:
                this.type = IntegerType.getInstance();
            case REF:
                /// Se da con expresion `ptr x`,
                /// que sirve para obtener el puntero a la variable x
                this.type = new PointerType(expr.type());
            case LEN:
                this.type = IntegerType.getInstance();
            default:
                throw new IllegalArgumentException("Invalid operator '" + op + "'");
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
                out.i32_const(((DefVar) (((Name) expr).getDefinition())).getDelta()); // Aqui hacemos i32.const
                                                                                      // delta(*id)
                                                                                      // (TODO: revisar)
                out.i32_add();
                break;
            case LEN:
                // El tamaño de un array se guarda dereferenciando el array, en su primera
                // posición
                expr.compileAsExpr(out);
                break;
            default:
                throw new IllegalArgumentException("Invalid operator '" + op + "'");
        }
    }

}
