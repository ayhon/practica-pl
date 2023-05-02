package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.IntegerType;

public class Natural extends Literal {
    private final int value;

    public Natural(int value) {
        this.value = value;
        this.type = IntegerType.getInstance();
    }

    public Natural(String lexeme) {
        this(Integer.parseInt(lexeme));
    }

    @Override
    public String getAstString() {
        return "nat";
    }

    public Object getValue() {
        return value;
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(value);
    }

    @Override
    public String decompile() {
        return Integer.toString(this.value);
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        out.i32_const(this.value);
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList();
    }

    @Override
    public Integer evalIntAtCompileTime() {
        return this.value;
    }
}
