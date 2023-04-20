package ditto.ast.types;

import ditto.ast.literals.Natural;

public class ArrayType implements Type {
    private final Type elementType;
    private final Integer size;

    /// If size is null, then this type definition is only possible
    /// in the arguments of a function definition.
    public ArrayType(Type elementType) {
        this(elementType, new Natural(0));
    }

    public ArrayType(Type elementType, Natural size) {
        this.elementType = elementType;
        this.size = (int) size.getValue();
    }

    public Type getElementType() {
        return elementType;
    }

    public Integer getSize() {
        return size;
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
            return elementType.equals(other.elementType);
        } else {
            return false;
        }
    }
}
