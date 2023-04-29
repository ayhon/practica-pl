package ditto.ast.types;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.literals.Natural;

public class ArrayType extends Type {
    private final Type elementType;
    private final int size;

    /// If size is null, then this type definition is only possible
    /// in the arguments of a function definition.
    public ArrayType(Type elementType) {
        this(elementType, -1);
    }

    public ArrayType(Type elementType, Natural size) {
        this(elementType, (int) size.getValue());
    }

    public ArrayType(Type elementType, int size) {
        this.elementType = elementType;
        this.size = size;
    }

    public Type getElementType() {
        return elementType;
    }

    public int getSize() {
        if (this.size == -1)
            return -1; /// Para caso de que sea un argumento de una funcion
        return size * this.elementType.size();
    }

    @Override
    public String toString() {
        return "ARRAY";
    }

    @Override
    public boolean equals(Object obj) {
        /// Dos arrays son del mismo tipo si tienen el mismo tipo de elementos
        if (obj instanceof ArrayType) {
            ArrayType other = (ArrayType) obj;
            if(other.elementType == null) return true; // We're comparing with an empty array
            return elementType.equals(other.elementType);
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return size * elementType.size();
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(elementType);
    }
}
