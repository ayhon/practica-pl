package ditto.ast.types;

import java.util.HashMap;
import java.util.Map;


public class StructType implements Type {
    private final String name;
    private final Map<String,Type> fieldTypes;
    private final Map<String, Type> returnFuncTypes;


    public Map<String,Type> getFieldTypes() {
        return fieldTypes;
    }
    public Map<String, Type> getReturnFuncTypes(){
        return returnFuncTypes;
    }

    public String getName() {
        return name;
    }

    public StructType(String name){
        this.name = name;
        this.fieldTypes = new HashMap<>();
        this.returnFuncTypes = new HashMap<>();
    }

    public StructType(String name, Map<String, Type> fieldTypes){
        this.name = name;
        this.fieldTypes = fieldTypes;
        this.returnFuncTypes = new HashMap<>();
    }

    public StructType(String name, Map<String,Type> fieldTypes, Map<String, Type> returnFuncTypes){
        this.name = name;
        this.fieldTypes = fieldTypes;
        this.returnFuncTypes = returnFuncTypes;
    }

    @Override
    public String toString() {
        return "STRUCT";
    }
}