package ditto.ast.statements;

import java.util.ArrayList;

import ditto.ast.expressions.Expr;

public class Case {
    public final Expr expr;
    public final ArrayList<Statement> body;

    public Case(){
        this.expr = null;
        this.body = null;
    }

    public Case(Expr expr, ArrayList<Statement> body) {
        this.expr = expr;
        this.body = body;
    }
}
