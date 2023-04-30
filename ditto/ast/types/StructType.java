package ditto.ast.types;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ditto.ast.Node;
import ditto.ast.Identifier;
import ditto.ast.Context;
import ditto.ast.definitions.DefStruct;
import ditto.ast.definitions.Definition;
import ditto.ast.literals.Literal;
import ditto.ast.literals.StructLiteral;
import ditto.errors.BindingError;
import ditto.ast.expressions.Expr;

public class StructType extends Type {
    private final Identifier iden;
    private Map<String, Type> fieldTypes;
    private DefStruct definition;
    private Literal defaultValue = null;
    private int size = 0;

    public StructType(Identifier iden) {
        /// Este constructor se utiliza para CUP
        /// Para cuando declaramos una variable del tipo struct
        this.iden = iden;
    }

    public StructType(Identifier iden, Map<String, Type> fieldTypes) {
        this.iden = iden;
        this.fieldTypes = fieldTypes;
    }

    @Override
    public Literal getDefault() {
        /// El valor por defecto de un struct es el valor por defecto de cada uno de sus
        /// campos
        if (definition == null)
            throw new BindingError("Can't construct default value before binding.");

        if (defaultValue != null)
            return defaultValue;

        Map<String, Expr> fieldValues = new HashMap<>();
        for (String fieldName : fieldTypes.keySet()) {
            fieldValues.put(fieldName, fieldTypes.get(fieldName).getDefault());
        }

        defaultValue = new StructLiteral(iden, fieldValues);
        return defaultValue;
    }

    public Identifier getIden() {
        return iden;
    }

    public Map<String, Type> getFieldTypes() {
        return Collections.unmodifiableMap(fieldTypes);
    }

    public Type getFieldOrMethodType(String name) {
        if (definition == null)
            throw new BindingError("Can't get the field types before binding.");

        if (fieldTypes.containsKey(name)) {
            return fieldTypes.get(name);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("Struct(%s)", iden);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<Node> getAstChildren() {
        return fieldTypes.values().stream().map(t -> (Node) t).toList();
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
    public void bind(Context ctx) {
        Definition def = ctx.get(this.iden);
        if (def == null)
            throw new BindingError("Couldn't find def " + iden);
        definition = (DefStruct) def;
        fieldTypes = definition.getType().getFieldTypes();
        super.bind(ctx);
    }

    @Override
    public void computeTypeSize() {
        super.computeTypeSize(); // Compute size of children
        for (Type type : fieldTypes.values()) {
            size += type.size(); // Add size of all attributes
        }
    }

}