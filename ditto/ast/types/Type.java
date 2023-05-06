package ditto.ast.types;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;

public abstract class Type extends Node {
    /// Si es un tipo básico o no (ocupa más de 4 bytes)
    public final boolean isBasic;

    public Type(boolean isBasic) {
        this.isBasic = isBasic;
    }

    /// Lo que ocuparía en memoria. Se usa para calcular delta.
    public abstract int size();

    public void compile(ProgramOutput out) {
        /* Los tipos no se compilan */
    }

    public String getAstString() {
        return null;
    }

    public List<Object> getAstArguments() {
        return new ArrayList<Object>();
    }

    public List<Node> getAstChildren() {
        return new ArrayList<Node>();
    }
}
