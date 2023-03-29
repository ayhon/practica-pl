package ditto.ast.expresions;

import ditto.ast.types.Type;
import ditto.ast.types.BoolType;

public class True extends Expresions{

    private True() {
        this.type = BoolType.TRUE;
    }

    @Override
    public int eval() {
        return 1;
    }

    @Override
    public Type getTipo() {
        return type;
    }
    @Override
    public String toString() {
        return "( true )";
    }
}
