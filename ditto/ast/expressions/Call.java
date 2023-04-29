package ditto.ast.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.designators.Designator;
import ditto.ast.types.FuncType;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class Call extends Expr {
    private final Designator func;
    private final List<Expr> args;
    private Type type;

    public Call(Designator func) {
        this.func = func;
        this.args = new ArrayList<>();
    }

    public Call(Designator func, List<Expr> args) {
        this.func = func;
        this.args = args;
    }

    @Override
    public String getAstString() {
        return "call";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(func, args);
    }

    public List<Expr> getArgs() {
        return args;
    }

    public Designator getFunc() {
        return func;
    }

    @Override
    public Type type() {
        return type;
    }

    @Override
    public void typecheck() {
        /// Hay que comprobar que el designator sea una funcion
        super.typecheck();
        if (!(this.func.type() instanceof FuncType)) {
            throw new TypeError(String.format("'%s' is not a function", this.func));
        }
        this.type = ((FuncType) this.func.type()).getReturnType();
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(func);
        children.addAll(args);
        return children;
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        throw new UnsupportedOperationException("Unimplemented method 'compileAsExpr'");
    }
}
