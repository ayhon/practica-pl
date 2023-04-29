package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Context;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.statements.Statement;
import ditto.ast.types.FuncType;
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;

public class DefFunc extends Definition {
    private final String id;
    private final List<Param> params;
    private final List<Statement> body;
    private final Type result;
    private int varSize;
    private FuncType type;

    public List<Param> getParams() {
        return params;
    }

    public Type getResult() {
        return result;
    }

    public DefFunc(String id, List<Param> params, List<Statement> body) {
        this(id, params, VoidType.getInstance(), body);
    }

    public DefFunc(String id, List<Param> params, Type result, List<Statement> body) {
        this.id = id;
        this.params = params;
        this.result = result;
        this.body = body;
        List<Type> paramTypes = new ArrayList<>();
        for (Param param : params) {
            paramTypes.add(param.getType());
        }
        type = new FuncType(result, paramTypes);
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

    static public class Param extends DefVar {
        private final boolean isRef;

        public Param(Type type, String name, Boolean isRef) {
            super(type, name);
            this.isRef = isRef;
        }

        @Override
        public String toString() {
            if (this.isRef)
                return "REF " + this.getType() + " " + this.getIden();
            else
                return this.getType() + " " + this.getIden();
        }
    }

    @Override
    public void bind(Context ctx) {
        /// Llama a bind de los hijos con nuevo contexto local
        ctx.add(this);
        ctx.pushScope(this);
        super.bind(ctx);
        ctx.popScope();
    }

    public Type getType() {
        return type;
    }

    @Override
    public Type type() {
        return getType();
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
        children.add(result);
        return children;
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }

    @Override
    public int computeDelta(int lastPosition) {
        int delta = type().size();
        for (Statement stmt : body) {
            delta = stmt.computeDelta(delta);
        }
        return lastPosition;
    }

    public int computeMaxFuncSize() {
        int max = type.size();

        for (Param param : params) {
            max += param.getType().size();
        }

        for (Statement stmt : body) {
            max += stmt.computeMaxFuncSize();
        }

        varSize = max;
        return 0;
    }
}