package ditto.ast.types;

// Singleton que sirve para el nul y las funciones que devuelven void
public class VoidType implements Type {
    private static VoidType instance = new VoidType();
    private VoidType() {}
    public static VoidType getInstance() {
        return instance;
    }
}
