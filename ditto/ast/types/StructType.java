package ditto.ast.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StructType implements Type {
    private final String name;
    private final List<String> module;
    private final Map<String, Type> fieldTypes;
    private final Map<String, Type> methodTypes;

    public StructType(String name, Map<String, Type> fieldTypes, Map<String, Type> methodTypes) {
        this(new ArrayList<>(), name, fieldTypes, methodTypes);
    }

    public StructType(List<String> module, String name, Map<String, Type> fieldTypes, Map<String, Type> methodTypes) {
        this.name = name;
        this.module = module;
        this.fieldTypes = fieldTypes;
        this.methodTypes = methodTypes;
    }

    public Map<String, Type> getFieldTypes() {
        return Collections.unmodifiableMap(fieldTypes);
    }

    public Map<String, Type> getMethodTypes() {
        return Collections.unmodifiableMap(methodTypes);
    }

    public String getName() {
        return name;
    }

    public List<String> getModule() {
        return module;
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
}