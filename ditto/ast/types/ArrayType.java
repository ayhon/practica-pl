package ditto.ast.types;

public class ArrayType implements Type {
    private final Type elementType;
    public Type getElementType() {
        return elementType;
    }
    private final Integer size;
    public Integer getSize() {
        return size;
    }
    /// If size is null, then this type definition is only possible 
    /// in the arguments of a function definition.
    public ArrayType(Type elementType) {
        this(elementType, null);
    }
    public ArrayType(Type elementType, Integer size) {
        this.elementType = elementType;
        this.size = size;
    }
}
