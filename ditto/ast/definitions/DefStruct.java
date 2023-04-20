package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.StructType;
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
        Map<String, Type> fieldTypes = new HashMap<>();
        Map<String, Type> methodTypes = new HashMap<>();

        for (DefVar attribute : attributes) {
            fieldTypes.put(attribute.getIden(), attribute.type());
        }

        for (DefFunc method : methods) {
            methodTypes.put(method.getId(), method.type());
        }

        return new StructType(name, fieldTypes, methodTypes);
    }

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }
}