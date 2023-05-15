package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.BoolType;

// Singleton
public class True extends Literal {
    private static True instance = new True();

    private True() {
        this.type = BoolType.getInstance();
    }

    public static True getInstance() {
        return instance;
    }

    @Override
    public String getAstString() {
        return "true";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList();
    }

    @Override
    public Object getValue() {
        return true;
    }

    @Override
    public Integer evalIntAtCompileTime() {
        return 1;
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        out.i32_const(1);
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList();
    }
}
