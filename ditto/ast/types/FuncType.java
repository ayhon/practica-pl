package ditto.ast.types;

import java.util.List;

import ditto.ast.Bindable;

public class FuncType extends Type {
    private final Type returnType;
    private final List<Type> argumentTypes;

    public FuncType(Type returnType, List<Type> argumentTypes) {
        this.returnType = returnType;
        this.argumentTypes = argumentTypes;
    }

    public List<Type> getArgumentTypes() {
        return argumentTypes;
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return "FUNC";
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
        int cont = 0;
        for (Type t : argumentTypes) {
            cont += t.size();
        }
        return cont;
    }

    @Override
    public List<Bindable> getBindableChildren() {
        List<Bindable> children = argumentTypes.stream().map(x -> (Bindable) x).toList();
        children.add(returnType);
        return children;
    }
}