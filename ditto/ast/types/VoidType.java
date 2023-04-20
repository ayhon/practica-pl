package ditto.ast.types;

// Singleton que sirve para el null y las funciones que devuelven void
public class VoidType implements Type {
    private static VoidType instance = new VoidType();

    private VoidType() {
    }

    public static VoidType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "VOID";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VoidType;
    }

    @Override
    public int size() {
        return 0;
    }
}
