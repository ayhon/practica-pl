package ditto.ast.literals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ditto.ast.Node;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;

public class StructLiteral extends Node implements Literal {

    private final String name;
    private final Map<String,Type> fieldTypes;
    private final Map<String,Literal> fieldValues;

    public StructLiteral(String name, Map<String,Type> fieldTypes, Map<String,Literal> fieldValues) {
        this.name = name;
        this.fieldTypes = fieldTypes;
        this.fieldValues = fieldValues;
    }
    
    @Override
    public String getAstString() { return "struct"; }

    @Override
    public List<Object> getAstArguments() { 
        ArrayList<Object> res = new ArrayList<Object>();
        res.add(name);
        res.add(fieldTypes);
        res.add(fieldValues);
        return res; 
    }

    @Override
    public Type getType() { return new StructType(name, fieldTypes); } 

    @Override
    public Object getValue() {
        return fieldValues;
    }
}