package ditto.ast.definitions;

import ditto.ast.types.Type;

public class Param {
    public final Type type;
    public final String name;
    public final Boolean isRef;

    public Param(Type type, String name) {
        /// Por valor por defecto
        this(type, name, false);
    }

    public Param(Type type, String name, Boolean isRef) {
        this.type = type;
        this.name = name;
        this.isRef = isRef;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
