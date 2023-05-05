package ditto.ast.types;

import ditto.ast.Node;
import ditto.ast.literals.Literal;
import ditto.ast.literals.Null;
import ditto.ast.CompilationProgress;

import java.util.ArrayList;
import java.util.List;

public class PointerType extends Type {
    private final Type elementType;
    private static final Null DEFAULT_POINTER = Null.getInstance();

    public PointerType(Type elementType) {
        super(true);
        this.elementType = elementType;
    }

    @Override
    public Literal getDefault() {
        return DEFAULT_POINTER;
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
        return 4;
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<>();
        if (elementType != null)
            children.add(elementType);
        return children;
    }

    @Override
    public void computeTypeSize() {
        this.setProgress(CompilationProgress.TYPE_SIZE);
    }
}