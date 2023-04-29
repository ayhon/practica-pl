package ditto.ast.types;

import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;

public abstract class Type extends Node {
    /// Lo que ocuparía en memoria. Se usa para calcular delta.
    public abstract int size();

    public Type type() {
        return null;
    }

    public void compile(ProgramOutput out) {
        /* TODO */ } // Desde Module llama a `type` antes de recursar,

    public String getAstString() {
        return null;
    }

    public List<Object> getAstArguments() {
        return null;
    }

    public List<Node> getAstChildren() {
        return null;
    }
}
