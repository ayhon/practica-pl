package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.GlobalContext;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.statements.Statement;
import ditto.ast.types.FuncType;
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;

public class DefFunc extends Definition {
    private final String id;
    private final List<Param> params;
    public List<Param> getParams() {
        return params;
    }

    private final Type result;

    public Type getResult() {
        return result;
    }

    private final List<Statement> body;

    public DefFunc(String id, List<Param> params, List<Statement> body) {
        this(id, params, VoidType.getInstance(), body);
    }

    public DefFunc(String id, List<Param> params, Type result, List<Statement> body) {
        this.id = id;
        this.params = params;
        this.result = result;
        this.body = body;
    }

    public String getIden() {
        return id;
    }

    @Override
    public String getAstString() {
        return "def-func";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(id, params, result, body);
    }

    static public class Param {
        public final Type type;
        public final String name;
        
        public Type getType() {
            return type;
        }
        public String getName() {
            return name;
        }

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
            if (this.isRef)
                return "REF " + type + " " + name;
            else
                return type + " " + name;
        }
    }

    @Override
    public void bind(GlobalContext global, LocalContext local) {
        /// Add the function to the global scope
        global.addGlobalFunction(this);
    }

    @Override
    public Type type() {
        List<Type> paramTypes = new ArrayList<>();
        for (Param param : params) {
            paramTypes.add(param.type);
        }
        return new FuncType(result, paramTypes);
    }

    @Override
    public void compile(ProgramOutput out) {
        out.func(this, () -> {
            for (Statement stmt : body) {
                stmt.compile(out);
            }
        });
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<>();
        children.addAll(body);
        return children;
    }
}
