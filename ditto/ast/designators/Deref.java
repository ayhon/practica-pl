package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;

public class Deref extends Node implements Designator {
    private final Designator pointer;

    public Designator getPointer() {
        return pointer;
    }

    public Deref(Designator pointer) {
        this.pointer = pointer;
    }

    @Override
    public Type getType() {
        Type ptr_type = pointer.getType();

        if (ptr_type instanceof PointerType) {
            return ((PointerType) ptr_type).getElementType();
        } else
            throw new IllegalArgumentException("Cannot dereference a non-pointer type");
    }

    @Override
    public String getAstString() {
        return "@";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(pointer);
    }

}
