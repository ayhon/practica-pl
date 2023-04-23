package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.BoolType;
import ditto.ast.types.IntegerType;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;
import ditto.errors.SemanticError;

public class OperUn extends Expr {
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
            case DEREF: 
                /// Se da con expresion `@x`,
                /// que sirve para obtener el valor apuntado por el puntero x
                if (expr.type() instanceof PointerType){
                    return ((PointerType) expr.type()).getElementType();
                }
                else throw new SemanticError("Cannot dereference non-pointer type");
            default:
                throw new IllegalArgumentException("Invalid operator '" + op + "'");
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

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(expr);
    }
}
