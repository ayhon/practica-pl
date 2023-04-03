package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;

public class Deref extends Node implements Designator {
    private final Designator pointer;
    private final Type type;
    public Designator getPointer() {
        return pointer;
    }
    public Deref(Designator pointer) {
        this.pointer = pointer;
        Type ptr_type = pointer.getType();
        if(ptr_type instanceof PointerType){
            this.type = ((PointerType)ptr_type).getElementType();
        } else throw new IllegalArgumentException("Cannot dereference a non-pointer type");
    }

    @Override
    public Type getType() { return type; }

    @Override
    public String getAstString() { return "@";}

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(pointer); }
    
}
