package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.BoolType;
import ditto.ast.types.Type;

// Singleton
public class True extends Node implements Literal {
    private static True instance = new True();
    private True() {}
    public static True getInstance() {
        return instance;
    }
    
    @Override
    public Type getType() {
        return BoolType.getInstance();
    }

    @Override
    public String getAstString() { return "true"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(); }
    
    @Override
    public Object getValue() {
        return true;
    }
}
