
package ditto.ast.types;

import ditto.ast.literals.Literal;
import ditto.ast.literals.Natural;

// Singleton
public class IntegerType extends Type {
    private static IntegerType instance = new IntegerType();
    private static final Natural DEFAULT_INT = new Natural(0);

    private IntegerType() {
    }

    @Override
    public Literal getDefault() {
        return DEFAULT_INT;
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
        return 1; /// Cada int ocupa un espacio en memoria de 4 bytes
    }
}