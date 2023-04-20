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

    public StructType(String name, Map<String, Type> fieldTypes, Map<String, Type> methodTypes) {
        this(new ArrayList<>(), name, fieldTypes, methodTypes);
    }

    public StructType(List<String> module, String name, Map<String, Type> fieldTypes, Map<String, Type> methodTypes) {
        this.name = name;
        this.module = module;
        this.fieldTypes = fieldTypes;
        this.methodTypes = methodTypes;
    }

    @Override
    public String toString() {
        return "STRUCT";
    }
}