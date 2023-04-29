
package ditto.ast.types;

// Singleton
public class IntegerType extends Type {
    private static IntegerType instance = new IntegerType();

    private IntegerType() {
    }

    public static IntegerType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "Integer";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntegerType;
    }

    @Override
    public int size() {
        return 1;       /// Cada int ocupa un espacio en memoria de 4 bytes
    }
}