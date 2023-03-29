package ditto.ast.expresions;

import ditto.ast.types.Type;
import ditto.ast.types.BoolType;

public class False extends Expresions{

    private False() {
        this.type = BoolType.FALSE;
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
        return "( False )";
    }
}