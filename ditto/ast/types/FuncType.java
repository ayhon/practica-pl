package ditto.ast.types;

import ditto.ast.Node;
import ditto.ast.literals.Literal;
import ditto.errors.SemanticError;


import java.util.ArrayList;
import java.util.List;

public class FuncType extends Type {
    private final Type returnType;
    private final List<Type> argumentTypes;
    private int argSize;

    public FuncType(Type returnType, List<Type> argumentTypes) {
        this.returnType = returnType;
        this.argumentTypes = argumentTypes;
        for (Type t : argumentTypes) {
            argSize += t.size();
        }
    }

    @Override
    public Literal getDefault() {
        throw new SemanticError("No se puede obtener el valor por defecto de una funcion");
    }

    public List<Type> getArgumentTypes() {
        return argumentTypes;
    }

    public Type getReturnType() {
        return returnType;
    }

    public int getNumberArgs(){
        return argumentTypes.size();
    }

    @Override
    public String toString() {
        return String.format("Func(%s) -> %s", argumentTypes, returnType);
    }

    @Override
    public boolean equals(Object obj) {
        /// Dos funciones son del mismo tipo si tienen los mismos tipos de argumentos y
        /// el mismo tipo de retorno
        if (obj instanceof FuncType) {
            FuncType other = (FuncType) obj;
            return returnType.equals(other.returnType) && argumentTypes.equals(other.argumentTypes);
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return 0;
    }

    public int getArgSize() {
        return argSize;
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<>();

        for (Type t : argumentTypes) {
            children.add(t);
        }

        children.add(returnType);
        return children;
    }
}