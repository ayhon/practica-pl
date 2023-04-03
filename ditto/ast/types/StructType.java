package ditto.ast.types;

import java.util.HashMap;
import java.util.Map;


public class StructType implements Type {
    private final String name;
    private final Map<String,Type> fieldTypes;
    public Map<String,Type> getFieldTypes() {
        return fieldTypes;
    }
    public String getName() {
        return name;
    }
    public StructType(String name){
        this.name = name;
        this.fieldTypes = new HashMap<>();
    }
    public StructType(String name, Map<String,Type> fieldTypes){
        this.name = name;
        this.fieldTypes = fieldTypes;
    }
}