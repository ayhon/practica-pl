package ditto.ast.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ditto.ast.GlobalContext;
import ditto.ast.LocalContext;
import ditto.ast.definitions.DefStruct;
import ditto.errors.SemanticError;

public class StructType implements Type {
    private final String name;
    private final String module;
    private Map<String, Type> fieldTypes;
    private DefStruct definition;

    public StructType(List<String> name) {
        /// Este constructor se utiliza para CUP
        /// Para cuando declaramos una variable del tipo struct
        if (name.size() == 1){
            this.name = name.get(0);
            this.module = null;
        } else if (name.size() == 2){
            this.name = name.get(1);
            this.module = name.get(0);
        } else throw new SemanticError("Nested modules aren't supported yet.");
    }

    public StructType(String name, Map<String, Type> fieldTypes) {
        this(null, name, fieldTypes);
    }

    public StructType(String module, String name, Map<String, Type> fieldTypes) {
        this.name = name;
        this.module = module;
        this.fieldTypes = fieldTypes;
    }

    public String getName() {
        return name;
    }

    public String getModule() {
        return module;
    }

    private void loadFieldTypesFromDefinition(){
        fieldTypes = new HashMap<>();
        for(var entry : definition.getAttributes()){
            String attribute = entry.getKey();
            Type type = entry.getValue().getType();
            fieldTypes.put(attribute, type);
        }
        for(var entry : definition.getMethods()){
            String method = entry.getKey();
            Type type = entry.getValue().getType();
            fieldTypes.put(method, type);
        }
    }

    public Type getFieldOrMethodType(String name) {
        if(fieldTypes == null && definition == null)
            throw new SemanticError("Can't get the field types before binding.");
        else if(fieldTypes == null)
            loadFieldTypesFromDefinition();

        if (fieldTypes.containsKey(name)) {
            return fieldTypes.get(name);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "STRUCT";
    }

    @Override
    public boolean equals(Object obj) {
        /// Dos structs son iguales si tienen el mismo nombre
        /// Porque no aceptamos Duck Typing
        if (obj instanceof StructType) {
            StructType other = (StructType) obj;
            return name.equals(other.name);
        } else {
            return false;
        }
    }

    public void bind(GlobalContext global, LocalContext local) {
        if(hasModule()){
            definition = global.getModule(module).getStruct(name);
        } else {
            definition = global.getStruct(name);
        }
    }

    private boolean hasModule() {
        return module != null;
    }

    @Override
    public int size() {
        /// Lo que ocupa un struct es la suma de lo que ocupan sus campos
        /// TODO: las funciones no ocupan nada?
        int size = 0;
        for (Type type : fieldTypes.values()) {
            size += type.size();
        }
        return size;
    }
}