package ditto.ast.literals;

import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.Type;

public class StructLiteral extends Node implements Literal {

    @Override
    public String getAstString() { return "struct"; }

    @Override
    public List<Object> getAstArguments() { return null; } // TODO: Implement this

    @Override
    public Type getType() { return null; } // TODO: Implement this

    @Override
    public String toString(){ return "{STRUCT NOT IMPLEMENTED}";}
}