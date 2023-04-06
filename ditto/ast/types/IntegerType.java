
package ditto.ast.types;

// Singleton
public class IntegerType implements Type {
    private static IntegerType instance = new IntegerType();
    private IntegerType() {}
    public static IntegerType getInstance() {
        return instance;
    }
}