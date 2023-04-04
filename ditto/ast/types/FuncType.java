package ditto.ast.types;

import java.util.List;

public class FuncType implements Type {
    private final Type returnType;

    public Type getReturnType() {
        return returnType;
    }

    private final List<Type> argumentTypes;

    public List<Type> getArgumentTypes() {
        return argumentTypes;
    }

    public FuncType(Type returnType, List<Type> argumentTypes) {
        this.returnType = returnType;
        this.argumentTypes = argumentTypes;
    }
}