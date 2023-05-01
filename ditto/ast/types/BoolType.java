package ditto.ast.types;

import ditto.ast.literals.False;
import ditto.ast.literals.Literal;

// Singleton
public class BoolType extends Type {
    private static BoolType instance = new BoolType();
    private static final False DEFAULT_BOOL = False.getInstance();

    private BoolType() {
    }

    @Override
    public Literal getDefault() {
        return DEFAULT_BOOL;
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
        return 4;
    }
}
