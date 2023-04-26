package ditto.ast.statements;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;

public abstract class Statement extends Node {
    public abstract void compileAsInstruction(ProgramOutput out);
}
