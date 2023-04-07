package ditto.ast.statements;

import java.util.ArrayList;

import ditto.ast.expressions.Expr;

public class Match implements Statement{
    private Expr expr;
    private ArrayList<Case> cases;
    private ArrayList<Statement> otherwise;

    public Match(Expr expr, ArrayList<Case> cases) {
        this.expr = expr;
        this.cases = cases;
        this.otherwise =  new ArrayList<Statement>();
    }

    public Match(Expr expr, ArrayList<Case> cases, ArrayList<Statement> otherwise) {
        this.expr = expr;
        this.cases = cases;
        this.otherwise = otherwise;
    }

    public String toString(){
        return "(match " + expr.toString() + " " + cases.toString() + " " + otherwise.toString() + ")";
    }

}
