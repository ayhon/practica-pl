package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.BoolType;
import ditto.ast.types.Type;

// Singleton
public class True extends Literal {
    private static True instance = new True();
    private Type type = BoolType.getInstance();

    private True() {}
    public static True getInstance() {
        return instance;
    }
    
    @Override
    public Type type() {
        return this.type;
    }

    @Override
    public String getAstString() { return "true"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(); }
    
    @Override
    public Object getValue() {
        return true;
    }
    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }
    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList();
    }
}
