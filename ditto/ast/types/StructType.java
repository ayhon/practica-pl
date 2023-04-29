package ditto.ast.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ditto.ast.GlobalContext;
import ditto.ast.Identifier;
import ditto.ast.LocalContext;
import ditto.ast.definitions.DefStruct;
import ditto.errors.SemanticError;

public class StructType implements Type {
    private final Identifier iden;
    private Map<String, Type> fieldTypes;
    private DefStruct definition;

    public StructType(Identifier iden) {
        /// Este constructor se utiliza para CUP
        /// Para cuando declaramos una variable del tipo struct
        this.iden = iden;
    }

    public StructType(Identifier iden, Map<String, Type> fieldTypes) {
        this.iden = iden;
        this.fieldTypes = fieldTypes;
    }

    public Identifier getIden() {
        return iden;
    }

    private void loadFieldTypesFromDefinition() {
        fieldTypes = new HashMap<>();
        for (var entry : definition.getAttributes()) {
            String attribute = entry.getKey();
            Type type = entry.getValue().getType();
            fieldTypes.put(attribute, type);
        }
        for (var entry : definition.getMethods()) {
            String method = entry.getKey();
            Type type = entry.getValue().getType();
            fieldTypes.put(method, type);
        }
    }

    public Type getFieldOrMethodType(String name) {
        if (fieldTypes == null && definition == null)
            throw new SemanticError("Can't get the field types before binding.");
        else if (fieldTypes == null)
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
            return iden.equals(other.iden);
        } else {
            return false;
        }
    }

    public void bind(GlobalContext global, LocalContext local) {
        if(iden.hasModule()){
            definition = global.getModule(iden.getModule()).getStruct(iden.getName());
        } else {
            definition = global.getStruct(iden.getName());
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