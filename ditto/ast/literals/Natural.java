package ditto.ast.literals;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;

public class Natural extends Node implements Literal {
    private final int value;

    public Natural(int value) {
        this.value = value;
    }
    
    public Natural(String lexeme) {
        value = Integer.parseInt(lexeme);
    }

    @Override
    public String getAstString() {
        return "nat";
    }
    public Object getValue() {
        return value;
    }
    
    @Override
    public Type getType() {
        return IntegerType.getInstance();
    }

    @Override
    public List<Object> getAstArguments() {
        return new ArrayList<Object>(value);
    }
}
