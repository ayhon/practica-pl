package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.Statement;
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;

public class DefFunc extends Node {
    private final String id;
    private final ArrayList<Param> params;
    private final Type result;
    private final ArrayList<Statement> body;

    public DefFunc(String id, ArrayList<Param> params, ArrayList<Statement> body) {
        this(id, params, new VoidType(), body);
    }

    public DefFunc(String id, ArrayList<Param> params, Type result, ArrayList<Statement> body) {
        this.id = id;
        this.params = params;
        this.result = result;
        this.body = body;
    }

    @Override
    public String toString() {
        return "(fun " + id + " " + params + " " + result + " " + body + ")";
    }

    @Override
    public String getAstString() {
        return "def-func";
    }

    @Override
    public List<Object> getAstArguments() {
        throw new UnsupportedOperationException("Unimplemented method 'getAstArguments' for DefFunc");
    }

}
