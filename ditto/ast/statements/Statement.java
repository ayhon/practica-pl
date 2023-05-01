package ditto.ast.statements;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.VoidType;

public abstract class Statement extends Node {
    public Statement() {
        this.type = VoidType.getInstance();
    }

    public abstract void compileAsInstruction(ProgramOutput out);

    @Override
    public void compile(ProgramOutput out) {
        // out.comment("INSTRUCCION: " + decompile());
        compileAsInstruction(out);
    }

    public String decompile(){ return getAstString(); }
}
