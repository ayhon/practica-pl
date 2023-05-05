package ditto.ast.types;

import ditto.ast.literals.Literal;
import ditto.errors.SemanticError;

// Singleton que sirve para el null y las funciones que devuelven void
public class VoidType extends Type {
    private static VoidType instance = new VoidType();

    private VoidType() {
        super(true);
    }

    @Override
    public Literal getDefault() {
        throw new SemanticError("No se puede obtener el valor por defecto de una funcion");
    }

    public static VoidType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "Void";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VoidType;
    }

    @Override
    public int size() {
        return 0;
    }
}
