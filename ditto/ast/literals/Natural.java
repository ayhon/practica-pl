package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;

public class Natural extends Literal {
    private final int value;

    public Natural(int value) {
        this.value = value;
    }

    public Natural(String lexeme) {
        value = Integer.parseInt(lexeme);
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
    public Type type() {
        this.type = IntegerType.getInstance();
        return this.type;
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsExpr'");
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
