package ditto.ast.types;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.literals.Literal;
import ditto.errors.TypeError;

public abstract class Type extends Node {
    /// Lo que ocuparía en memoria. Se usa para calcular delta.
    public abstract int size();

    public abstract Literal getDefault();

    public Type type() {
        throw new TypeError("What's the type of a type?");
    }

    public void compile(ProgramOutput out) {
        /* TODO */ } // Desde Module llama a `type` antes de recursar,

    public String getAstString() {
        return null;
    }

    public List<Object> getAstArguments() {
        return new ArrayList<Object>();
    }

    public List<Node> getAstChildren() {
        return new ArrayList<Node>();
    }

    public String asWasmResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("(result ");
        for(int i = 0; i < size(); i++) {
            sb.append("i32 ");
        }
        sb.append(")");
        return sb.toString();
    }
}
