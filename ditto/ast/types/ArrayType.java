package ditto.ast.types;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.literals.Natural;
import ditto.errors.SemanticError;

public class ArrayType extends Type {
    private final Type elementType;
    private int length;

    public ArrayType(Type elementType, Natural size) {
        this(elementType, (int) size.getValue());
    }

    public ArrayType(Type elementType, int length) {
        super(false);
        this.elementType = elementType;
        this.length = length;
    }

    public Type getElementType() {
        return elementType;
    }

    public int getLength() {
        if (this.length == -1)
            throw new SemanticError("Array length not defined");
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return String.format("Array(%s)", elementType);
    }

    @Override
    public boolean equals(Object obj) {
        /// Dos arrays son del mismo tipo si tienen el mismo tipo de elementos
        if (obj instanceof ArrayType) {
            ArrayType other = (ArrayType) obj;
            if (other.elementType == null || elementType == null)
                return true; // We're comparing with an empty array
            return elementType.equals(other.elementType);
        } else {
            return false;
        }
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<>();
        children.add(elementType);
        return children;
    }

    @Override
    public int size() {
        return getLength() * elementType.size();
    }
}
