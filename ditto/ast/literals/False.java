package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.BoolType;
import ditto.ast.types.Type;

// Singleton
public class False extends Node implements Literal {
    private static False instance = new False();

    private False() {
    }

    public static False getInstance() {
        return instance;
    }

    @Override
    public Type getType() {
        return BoolType.getInstance();
    }

    @Override
    public String getAstString() { return "false"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(); }

    @Override
    public String toString() { return getAstString(); }
}