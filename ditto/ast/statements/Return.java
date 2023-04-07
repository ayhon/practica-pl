package ditto.ast.statements;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.expressions.Expr;

public class Return extends Node implements Statement {
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
    
}
