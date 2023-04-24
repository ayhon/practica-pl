package ditto.ast.statements;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;

public class Return extends Statement {
    private final Expr expr;

    private Type type = null;

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
        this.type = expr.type();
        return this.type;
    }
    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }
    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        if(expr != null){
            children.add(expr);
        }
        return children;
    }
    
}
