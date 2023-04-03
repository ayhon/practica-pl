package ditto.ast.literals;

import ditto.ast.expressions.Expr;
import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;

public class Natural implements Expr {
    private final int value;

    public Natural(String lexeme) {
        value = Integer.parseInt(lexeme);
    }

    @Override
    public String toString() {
        return "( nat ( " + value + " ) )";
    }

    @Override
    public Type getType() {
        return IntegerType.getInstance();
    }
}
