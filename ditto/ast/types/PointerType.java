package ditto.ast.types;

import ditto.ast.Node;
import ditto.ast.literals.Literal;
import ditto.ast.literals.Null;
import ditto.ast.CompilationProgress;

import java.util.List;

public class PointerType extends Type {
    private final Type elementType;

    public PointerType(Type elementType) {
        this.elementType = elementType;
    }

    @Override
    public Literal getDefault() {
        return Null.getInstance();
    }

    public Type getElementType() {
        return elementType;
    }

    @Override
    public String toString() {
        return String.format("Pointer(%s)", elementType);
    }

    @Override
    public boolean equals(Object obj) {
        /// Dos punteros son del mismo tipo si lo son sus elementos
        if (obj instanceof PointerType) {
            PointerType other = (PointerType) obj;
            if (other.elementType == null)
                return true; // We're comparing with a null pointer
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
    public List<Node> getAstChildren() {
        return List.of(elementType);
    }

    @Override
    public void computeTypeSize() {
        this.setProgress(CompilationProgress.TYPE_SIZE);
    }
}