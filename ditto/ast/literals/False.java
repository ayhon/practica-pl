package ditto.ast.literals;

import ditto.ast.types.BoolType;
import ditto.ast.types.Type;

// Singleton
public class False implements Literal {
    private static False instance = new False();

    private False() {
    }

    public static False getInstance() {
        return instance;
    }

    @Override
    public Type getType() {
        return BoolType.getInstance();
    }
}