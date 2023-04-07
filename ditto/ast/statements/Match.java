package ditto.ast.statements;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import ditto.ast.Node;
import ditto.ast.expressions.Expr;

public class Match extends Node implements Statement{
    private Expr expr;
    private List<Case> cases;
    private List<Statement> otherwise;

    public Match(Expr expr, List<Case> cases) {
        this.expr = expr;
        this.cases = cases;
        this.otherwise =  new ArrayList<Statement>();
    }

    public Match(Expr expr, List<Case> cases, List<Statement> otherwise) {
        this.expr = expr;
        this.cases = cases;
        this.otherwise = otherwise;
    }

    static public class Case extends Node {
        public final Expr expr;
        public final List<Statement> body;
    
        public Case(){
            this.expr = null;
            this.body = null;
        }
    
        public Case(Expr expr, List<Statement> body) {
            this.expr = expr;
            this.body = body;
        }

        @Override
        public String getAstString() { return "case"; }

        @Override
        public List<Object> getAstArguments() { return Arrays.asList(expr, body); }

    }

    @Override
    public String getAstString() { return "match"; }

    @Override
    public List<Object> getAstArguments() { 
        List<Object> args = Arrays.asList(expr,cases);
        if(otherwise.size() > 0)
            args.add(otherwise);
        return args;
    }
}
