package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ditto.ast.Node;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;

public class 
StructAccess extends Node implements Designator {
    private final Designator struct;
    private final String name;
    private final Type type;
    public Designator getStruct() {
        return struct;
    }
    public String getField() {
        return name;
    }
    public StructAccess(Designator struct, String name) {
        this.name = name;
        Type struct_type = struct.getType();
        if (struct_type instanceof StructType) {
            this.struct = struct;
            Map<String, Type> field_types = ((StructType)struct_type).getFieldTypes();
            if((field_types.containsKey(name))){
                this.type = field_types.get(name);
            } else throw new IllegalArgumentException("Struct " + ((StructType)struct_type).getName() + " does not have a field named " + name);
        } else throw new IllegalArgumentException("Cannot access a field of a non-struct type");
    }

    @Override
    public Type getType() { return type; }

    @Override
    public String getAstString() { return "field"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(struct, name); }
    
}