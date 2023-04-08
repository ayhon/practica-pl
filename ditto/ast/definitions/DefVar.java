package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.expressions.Expr;
import ditto.ast.statements.Statement;
import ditto.ast.types.Type;

public class DefVar extends Node implements Statement {
    private final String name;
    private final Expr expr;
    private final Type type;

    public DefVar(Type type, String name, Expr expr) {
        // Argumentos en este orden para representar como se escribe en el lenguaje
        this.name = name;
        this.type = type;
        this.expr = expr;
    }

    public DefVar(Type type, String name) {
        this(type, name, null);
    }

    @Override
    public String getAstString() {
        return "let";
    }

    @Override
    public List<Object> getAstArguments() {
        List<Object> args = new ArrayList<Object>();
        args.add(type);
        args.add(name);
        if (this.expr != null)
            args.add(expr);
        return args;
    }
}
