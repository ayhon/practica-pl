package ditto.ast.definitions;

import java.util.List;

import ditto.ast.Node;
import ditto.ast.statements.Statement;
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;

public class DefFunc extends Node {
    private final String id;
    private final List<Param> params;
    private final Type result;
    private final List<Statement> body;

    public DefFunc(String id, List<Param> params, List<Statement> body) {
        this(id, params, VoidType.getInstance(), body);
    }

    public DefFunc(String id, List<Param> params, Type result, List<Statement> body) {
        this.id = id;
        this.params = params;
        if (params == null)
            throw new RuntimeException("params is null");
        this.result = result;
        this.body = body;
    }

    @Override
    public String getAstString() {
        return "def-func";
    }

    @Override
    public List<Object> getAstArguments() {
        throw new UnsupportedOperationException("Unimplemented method 'getAstArguments' for DefFunc");
    }
    static public class Param {
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
}
