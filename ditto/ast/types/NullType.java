package ditto.ast.types;

public class NullType implements Type {
    private final static NullType instance = new NullType();

    private NullType() {
    }

    public static NullType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "NULL";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NullType;
    }

    @Override
    public int size() {
        return 0;
    }
}
