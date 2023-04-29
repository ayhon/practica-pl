package ditto.ast.types;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import ditto.ast.Module;
import ditto.ast.Bindable;
import ditto.ast.Identifier;
import ditto.ast.LocalContext;
import ditto.ast.definitions.DefStruct;
import ditto.errors.SemanticError;

public class StructType extends Type {
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

    public Map<String, Type> getFieldTypes() {
        return Collections.unmodifiableMap(fieldTypes);
    }

    public Type getFieldOrMethodType(String name) {
        if (definition == null)
            throw new SemanticError("Can't get the field types before binding.");

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

    @Override
    public List<Bindable> getBindableChildren() {
        return fieldTypes.values().stream().map(t -> (Bindable) t).toList();
    }

    @Override
    public void bind(Module global, LocalContext local) {
        if (iden.hasModule()) {
            definition = global.getModule(iden.getModule()).getStruct(iden.getName());
        } else {
            definition = global.getStruct(iden.getName());
        }
        fieldTypes = definition.getType().getFieldTypes();
        super.bind(global, local);
    }
}