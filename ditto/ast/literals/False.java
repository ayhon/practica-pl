package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.BoolType;
import ditto.ast.types.Type;

// Singleton
public class False extends Literal {
    private static False instance = new False();

    private Type type = null;

    private False() {
    }

    public static False getInstance() {
        return instance;
    }

    @Override
    public Type type() {
        this.type = BoolType.getInstance();
        return this.type;
    }

    @Override
    public String getAstString() { return "false"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(); }

    @Override
    public Object getValue() {  return false;   }

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
    public void compileAsInstruction(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }
}