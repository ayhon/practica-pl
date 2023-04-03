package ditto.ast.literals;

import ditto.ast.expressions.Expr;
import ditto.ast.types.BoolType;
import ditto.ast.types.Type;

// Singleton
public class False implements Literal {
    private static False instance = new False();
    private False() {}
    public static False getInstance() {
        return instance;
    }
    @Override
    public int eval() {
        return 0;
    }
    @Override
    public Type getType() {
        return BoolType.getInstance();
    }
}