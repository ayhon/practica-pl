package ditto.ast.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefFunc;
import ditto.ast.designators.Designator;
import ditto.ast.types.Type;

public class Call extends Expr {
    private final Designator func;
    private final List<Expr> args;
    private DefFunc funcDef;

    public Call(Designator func) {
        this.func = func;
        this.args = new ArrayList<>(0);
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
        return funcDef.getResult();
    }

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

}
