package ditto.ast.types;

// Singleton
public class BoolType extends Type {
    private static BoolType instance = new BoolType();

    private BoolType() {
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
