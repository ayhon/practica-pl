package ditto.ast.definitions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.Statement;
import ditto.ast.types.Type;

public class DefVar extends Node implements Statement {
    private final String name;
    private final Type type;

    public DefVar(Type type, String name) {
        // Argumentos en este orden para representar como se escribe en el lenguaje
        this.name = name;
        this.type = type;
    }

    @Override
    public String getAstString() {
        return "let";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(name, type);
    }
}
