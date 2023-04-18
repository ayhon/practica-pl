package ditto.ast.statements;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;

public class Return extends Statement {
    private final Expr expr;
    public Return() {
        this.expr = null;
    }
    public Return(Expr expr) {
        this.expr = expr;
    }

    @Override
    public String getAstString() { return "return";}

    @Override
    public List<Object> getAstArguments() { 
        List<Object> args = new ArrayList<>();
        if(expr != null){
            args.add(expr);
        }    
        return args;
    }
    @Override
    public Type type() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'type'");
    }
    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }
    
}
