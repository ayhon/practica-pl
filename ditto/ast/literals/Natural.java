package ditto.ast.literals;

import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;

public class Natural implements Literal {
    private final int value;

    public Natural(int value) {
        this.value = value;
    }
    
    public Natural(String lexeme) {
        value = Integer.parseInt(lexeme);
    }

    public int getValue() {
        return value;
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
