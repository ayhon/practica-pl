package ditto.ast.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StructType implements Type {
    private final String name;
    private final List<String> module;
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

    public StructType(String iden, List<String> module){
        this.name = iden;
        this.module = module;
        this.fieldTypes = new HashMap<>();
        this.returnFuncTypes = new HashMap<>();
        
    }

    public StructType(List<String> name){
        this(name, new HashMap<>(), new HashMap<>());
    }

    public StructType(List<String> name, Map<String, Type> fieldTypes){
        this(name, fieldTypes, new HashMap<>());
    }
    public StructType(List<String> name, Map<String,Type> fieldTypes, Map<String, Type> returnFuncTypes){
        this.name = name.get(name.size() - 1);
        this.module = name.subList(0, name.size() - 1);
        this.fieldTypes = fieldTypes;
        this.returnFuncTypes = returnFuncTypes;
    }

    @Override
    public String toString() {
        return "STRUCT";
    }
}