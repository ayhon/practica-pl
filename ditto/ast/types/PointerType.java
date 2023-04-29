package ditto.ast.types;

import java.util.List;

import ditto.ast.Bindable;

public class PointerType extends Type {
    private final Type elementType;

    public PointerType(Type elementType) {
        this.elementType = elementType;
    }

    public Type getElementType() {
        return elementType;
    }

    @Override
    public String toString() {
        return "PTR";
    }

    @Override
    public boolean equals(Object obj) {
        /// Dos punteros son del mismo tipo si lo son sus elementos
        if (obj instanceof PointerType) {
            PointerType other = (PointerType) obj;
            return elementType.equals(other.elementType);
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public List<Bindable> getBindableChildren() {
        return List.of(elementType);
    }
}
