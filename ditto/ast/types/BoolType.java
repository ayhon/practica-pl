package ditto.ast.types;

// Singleton
public class BoolType implements Type {
    
    private static BoolType instance = new BoolType();

    private BoolType() {
    }

    public static BoolType getInstance() {
        return instance;
    }
}
