package ditto.ast.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructType implements Type {
    private final String name;
    private final List<String> module;
    private final Map<String, Type> fieldTypes;
    private final Map<String, Type> methodTypes;

    public StructType(List<String> name) {
        /// Este constructor se utiliza para CUP
        /// Para cuando declaramos una variable del tipo struct
        this(name.subList(0, name.size() - 1), name.get(name.size() - 1), new HashMap<>(), new HashMap<>());
    }

    public StructType(String name, Map<String, Type> fieldTypes, Map<String, Type> methodTypes) {
        this(new ArrayList<>(), name, fieldTypes, methodTypes);
    }

    public StructType(List<String> module, String name, Map<String, Type> fieldTypes, Map<String, Type> methodTypes) {
        this.name = name;
        this.module = module;
        this.fieldTypes = fieldTypes;
        this.methodTypes = methodTypes;
    }

    public String getName() {
        return name;
    }

    public List<String> getModule() {
        return module;
    }

    public Type getFieldOrMethodType(String name) {
        if (fieldTypes.containsKey(name)) {
            return fieldTypes.get(name);
        } else if (methodTypes.containsKey(name)) {
            return methodTypes.get(name);
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