package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Delta;
import ditto.ast.CompilationProgress;
import ditto.ast.Context;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.statements.Return;
import ditto.ast.statements.Statement;
import ditto.ast.types.FuncType;
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;
import ditto.errors.TypeError;

public class DefFunc extends Definition {
    private final List<Param> params;
    private final List<Statement> body;
    private final Type result;
    private int size;
    private Boolean external = false;

    public List<Param> getParams() {
        return params;
    }

    public List<Statement> getBody() {
        return body;
    }

    public Type getResult() {
        return result;
    }

    public int getSize() {
        return size;
    }

    public DefFunc(String id, List<Param> params, Type result) {
        this(id, params, result, new ArrayList<Statement>());
        this.external = true;
    }

    public DefFunc(String id, List<Param> params, List<Statement> body) {
        this(id, params, VoidType.getInstance(), body);
    }

    public DefFunc(String id, List<Param> params, Type result, List<Statement> body) {
        super(id);
        this.params = params;
        this.result = result;
        this.body = body;
        List<Type> paramTypes = new ArrayList<>();
        for (Param param : params) {
            paramTypes.add(param.getType());
        }
        type = new FuncType(result, paramTypes);
    }

    static public class Param extends DefVar {
        private final boolean isRef;

        public Param(Type type, String name, Boolean isRef) {
            super(type, name);
            this.isRef = isRef;
        }

        public boolean isRef() {
            return isRef;
        }

        @Override
        public String toString() {
            if (this.isRef)
                return "REF " + this.getType() + " " + this.getIden();
            else
                return this.getType() + " " + this.getIden();
        }

        @Override
        public void computeOffset(Delta delta) {
            int size;

            if (this.isRef)
                size = 4;
            else
                size = this.getType().size();

            position = delta.useNextOffset(size);
        }

    }

    public Type getType() {
        return type;
    }

    public Boolean isExternal() {
        return external;
    }

    @Override
    public String getAstString() {
        String output = "def-func";

        if (this.getProgress().atLeast(CompilationProgress.FUNC_SIZE_AND_DELTAS))
            output += String.format(" [size = %d]", this.size);

        return output;
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(this.getIden(), params, result, body);
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<>();
        children.addAll(params);
        children.add(type);
        children.addAll(body);
        return children;
    }

    @Override
    public void bind(Context ctx) {
        /// Llama a bind de los hijos con nuevo contexto local
        ctx.add(this);
        ctx.pushScope();
        super.bind(ctx);
        ctx.popScope();
    }

    @Override
    public void typecheck() {
        super.typecheck();
        Type expected = ((FuncType) type).getReturnType();
        boolean hasReturn = false;
        for (Statement stmt : body) {
            if (stmt instanceof Return) {
                hasReturn = true;
                Type actual = ((Return) stmt).getExpr().type();
                if (!actual.equals(expected)) {
                    throw new TypeError("Can't return " + actual + " in a function that returns " + expected);
                }
            }
        }
        if (hasReturn && expected.equals(VoidType.getInstance())) {
            throw new TypeError("Function " + getIden() + " must return " + expected);
        }
    }

    @Override
    public void computeOffset(Delta delta) {
        Delta d = new Delta();
        super.computeOffset(d);
        size = d.getOffsetSize();
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
    public void compileAsInstruction(ProgramOutput out) {
        out.func(this, () -> {
            for (Statement stmt : body) {
                stmt.compileAsInstruction(out);
            }
        });
    }

}
