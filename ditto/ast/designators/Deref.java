package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;

public class Deref extends Designator {
    private final Designator pointer;

    public Designator getPointer() {
        return pointer;
    }

    public Deref(Designator pointer) {
        this.pointer = pointer;
    }

    @Override
    public Type type() {
        Type ptr_type = pointer.type();

        if (ptr_type instanceof PointerType) {
            return ((PointerType) ptr_type).getElementType();
        } else {
            throw new IllegalArgumentException("Cannot dereference a non-pointer type");
        }
    }

    @Override
    public String getAstString() {
        return "@";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(pointer);
    }

    @Override
    public void compileAsDesig(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsDesig'");
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(pointer);
    }


}
