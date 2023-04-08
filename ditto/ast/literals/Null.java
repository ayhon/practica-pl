package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;


//Singleton
public class Null extends Node implements Literal {
    private static PointerType type = new PointerType(null);


    private Null() {}

    public static True getInstance() {
        return null;
    }
    
    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getAstString() { return "null"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(); }
    
    @Override
    public Object getValue() {
        return true;
    }
}
