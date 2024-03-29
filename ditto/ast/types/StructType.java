package ditto.ast.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ditto.ast.Node;
import ditto.ast.Identifier;
import ditto.ast.Context;
import ditto.ast.definitions.DefFunc;
import ditto.ast.definitions.DefStruct;
import ditto.ast.definitions.DefVar;
import ditto.ast.definitions.Definition;
import ditto.errors.BindingError;

public class StructType extends Type {
    private final Identifier iden;
    private Map<String, Type> fieldTypes;
    private DefStruct definition;
    private int size = 0;

    public StructType(Identifier iden) {
        /// Este constructor se utiliza para CUP
        /// Para cuando declaramos una variable del tipo struct
        super(false);
        this.iden = iden;
    }

    public StructType(DefStruct def) {
        super(false);
        this.iden = new Identifier(def.getIden());
        this.definition = def;
    }

    public Definition getFieldDefinition(String name) {
        return definition.getAttributeOrMethod(name);
    }

    public DefStruct getDefinition() {
        return definition;
    }

    public Identifier getIden() {
        return iden;
    }

    public int getOffset(String name) {
        /// Devuelve el offset de un campo del struct
        return definition.getOffset(name);
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
        List<Node> children = new ArrayList<>();
        children.addAll(fieldTypes.values().stream().map(t -> (Node) t).toList());
        return children;
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
        if (this.definition == null) {
            Definition def = ctx.get(this.iden);
            if (def == null)
                throw new BindingError("Couldn't find def " + iden);
            definition = (DefStruct) def;
        }

        this.fieldTypes = new HashMap<>();
        for (DefVar attribute : this.definition.getAttributes().values()) {
            fieldTypes.put(attribute.getIden(), attribute.getType());
        }
        for (DefFunc method : this.definition.getMethods().values()) {
            fieldTypes.put(method.getIden(), method.getType());
        }

        super.bind(ctx);
    }

    @Override
    public void computeTypeSize() {
        super.computeTypeSize(); // Compute size of children
        for (Type type : fieldTypes.values()) {
            this.size += type.size(); // Add size of all attributes
        }
    }
}