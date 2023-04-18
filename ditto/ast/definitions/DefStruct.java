package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.Type;

public class DefStruct extends Node {
    private final String name;
    private final List<DefVar> attributes;
    private final List<DefFunc> methods;

    public DefStruct(String name) {
        this.name = name;
        this.attributes = new ArrayList<>(0);
        this.methods = new ArrayList<>(0);
    }

    public DefStruct(String name, List<DefVar> attributes, List<DefFunc> methods) {
        this.name = name;
        this.attributes = attributes;
        this.methods = methods;
    }

    @Override
    public String getAstString() {
        return "def-struct";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(name, attributes, methods);
    }

    @Override
    public Type type() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'type'");
    }

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }
}