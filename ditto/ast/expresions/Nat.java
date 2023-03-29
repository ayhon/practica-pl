package ditto.ast.expresions;

import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;

public class Nat extends Expresions {
    private int value = 0;

    Nat(String lexeme) {
        this.value = Integer.parseInt(lexeme);
        this.type = new IntegerType();
    }

    @Override
    public int eval() {
        return value;
    }

    @Override
    public String toString() {
        return "( nat ( " + value + " ) )";
    }

    @Override
    public Type getTipo() {
        return type;
    }
}
