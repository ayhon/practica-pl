package ditto.ast.types;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.literals.Literal;

public abstract class Type extends Node {
    /// Lo que ocuparía en memoria. Se usa para calcular delta.
    public abstract int size();

    public abstract Literal getDefault();

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
