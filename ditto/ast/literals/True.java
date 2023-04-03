package ditto.ast.literals;

import ditto.ast.expressions.Expr;
import ditto.ast.types.BoolType;
import ditto.ast.types.Type;

// Singleton
public class True implements Expr {
    private static True instance = new True();
    private True() {}
    public static True getInstance() {
        return instance;
    }
    @Override
    public Type getType() {
        return BoolType.getInstance();
    }
}
