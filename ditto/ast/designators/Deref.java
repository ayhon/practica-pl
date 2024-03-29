package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class Deref extends Designator {
    private final Designator pointer;

    public Designator getPointer() {
        return pointer;
    }

    public Deref(Designator pointer) {
        this.pointer = pointer;
    }

    @Override
    public void typecheck() {
        super.typecheck();
        Type ptr_type = pointer.type();

        if (ptr_type instanceof PointerType) {
            type = ((PointerType) ptr_type).getElementType();
            if (type == null)
                throw new TypeError("You can't dereference null");
        } else
            throw new TypeError(String.format("Cannot dereference a non-pointer type '%s'", ptr_type));

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
    public String decompile() {
        return String.format("ptr %s", pointer.decompile());
    }

    @Override
    public void compileAsDesig(ProgramOutput out) {
        pointer.compileAsDesig(out);
        out.i32_load();
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(pointer);
    }
}
