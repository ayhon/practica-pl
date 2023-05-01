package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.ArrayType;
import ditto.ast.types.BoolType;
import ditto.ast.types.IntegerType;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;
import ditto.errors.TypeError;
import ditto.ast.designators.Designator;

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
                /// Guarreria:
                if (!(this.expr.type() instanceof ArrayType)) {
                    throw new TypeError(
                            String.format("No se puede calcular la longitud de algo que no es un array"));
                } else {
                    return null;
                }
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
        if (this.op == Operators.REF && !(this.expr instanceof Designator)) {
            throw new TypeError(String
                    .format("No se puede aplicar el operador '%s' a una expresión que no sea un designator", this.op));
        }
        this.type = computeType();
    }

    private Type computeType() {
        switch (this.op) {
            case NOT -> {
                this.type = BoolType.getInstance();
            }
            case NEG -> {
                this.type = IntegerType.getInstance();
            }
            case POS -> {
                this.type = IntegerType.getInstance();
            }
            case REF -> {
                /// Se da con expresion `ptr x`,
                /// que sirve para obtener el puntero a la variable x
                this.type = new PointerType(expr.type());
            }
            case LEN -> {
                this.type = IntegerType.getInstance();
            }
            default -> {
                throw new IllegalArgumentException("Invalid operator '" + op + "'");
            }
        }

        return this.type;
    }

    @Override
    public String decompile() {
        return String.format("%s %s", this.op, this.expr.decompile());
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        switch (this.op) {
            case NOT -> {
                // Se da con expresion `not x`,
                out.i32_const(1);
                expr.compileAsExpr(out);
                out.i32_xor();
            }
            case NEG -> {
                // Se da con expresion `-x`,
                expr.compileAsExpr(out);
                out.i32_const(0);
                out.i32_sub();
            }
            case POS -> {
                // Se da con expresion `+x`,
                expr.compileAsExpr(out);
            }
            case REF -> {
                // Se da con expresion `ptr x`,
                // Cargamos la direccion de x y la dejamos en el tope de la pila
                ((Designator) expr).compileAsDesig(out);
            }
            case LEN -> {
                // El tamaño de un array se guarda dereferenciando el array, en su primera
                // posición #a
                out.i32_const(
                        ((ArrayType) expr.type()).getLength());
            }
            default -> {
                throw new IllegalArgumentException("Invalid operator '" + op + "'");
            }
        }
    }

    @Override
    public Integer evalIntAtCompileTime() {
        Integer val = expr.evalIntAtCompileTime();
        if (val == null) {
            return null;
        }

        switch (op) {
            case NEG -> {
                return -val;
            }
            default -> {
                return null;
            }
        }
    }
}
