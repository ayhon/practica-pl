package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.PointerType;

//Singleton
public class Null extends Literal {
    private static Null instance = new Null();

    private Null() {
        this.type = new PointerType(null);
    }

    public static Null getInstance() {
        return instance;
    }

    @Override
    public String getAstString() {
        return "null";
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
    public void compileAsExpr(ProgramOutput out) {
        out.i32_const(0);
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList();
    }
}
