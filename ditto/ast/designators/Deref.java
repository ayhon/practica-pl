package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Module;
import ditto.ast.LocalContext;
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
    public Type type() {
        /// Nota: typecheck() ya se encarga de chequear que pointer sea un puntero
        Type ptr_type = pointer.type();
        return ((PointerType) ptr_type).getElementType();
    }

    @Override
    public void typecheck() {
        Type ptr_type = pointer.type();

        if (!(ptr_type instanceof PointerType)) {
            throw new TypeError(String.format("Cannot dereference a non-pointer type '%s'", ptr_type));
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
        throw new UnsupportedOperationException("Unimplemented method 'compileAsDesig'");
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(pointer);
    }
}
