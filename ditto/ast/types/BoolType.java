package ditto.ast.types;

import ditto.ast.literals.False;
import ditto.ast.literals.Literal;

// Singleton
public class BoolType extends Type {
    private static BoolType instance = new BoolType();

    private BoolType() {
    }

    @Override
    public Literal getDefault() {
        return False.getInstance();
    }

    public static BoolType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "Bool";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public int size() {
        return 1;
    }
}
