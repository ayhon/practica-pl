package ditto.ast.types;

public class PointerType implements Type {
    private final Type elementType;
    
    public Type getElementType() {
        return elementType;
    }
    public PointerType(Type elementType) {
        this.elementType = elementType;
    }

}
