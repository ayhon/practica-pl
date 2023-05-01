package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.BoolType;
// Singleton
public class False extends Literal {
    private static False instance = new False();

    private False() {
        this.type = BoolType.getInstance();
    }

    public static False getInstance() {
        return instance;
    }

    @Override
    public String getAstString() {
        return "false";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList();
    }

    @Override
    public Object getValue() {
        return false;
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