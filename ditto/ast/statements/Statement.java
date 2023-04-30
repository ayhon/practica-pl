package ditto.ast.statements;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;

public abstract class Statement extends Node {
    protected Type type = VoidType.getInstance();

    public abstract void compileAsInstruction(ProgramOutput out);

    @Override
    public void compile(ProgramOutput out) {
        compileAsInstruction(out);
    }
}
