package ditto.ast.literals;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;


//Singleton
public class Null extends Literal {
    private static PointerType type = new PointerType(null);
    private static Null instance = new Null();

    private Null() {}

    public static Null getInstance() {
        return instance;
    }
    
    @Override
    public Type type() {
        return type;
    }

    @Override
    public String getAstString() { return "null"; }

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
