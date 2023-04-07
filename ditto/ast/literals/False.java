package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.BoolType;
import ditto.ast.types.Type;

// Singleton
public class False extends Node implements Literal {
    private static False instance = new False();
    private static Type type = BoolType.getInstance();
    private False() {
    }

    public static False getInstance() {
        return instance;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getAstString() { return "false"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(); }

    @Override
    public Object getValue() {  return false;   }
}