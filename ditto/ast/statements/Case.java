package ditto.ast.statements;

import java.util.ArrayList;

import ditto.ast.expressions.Expr;

public class Case {
    public final Expr expr;
    public final ArrayList<Statement> cuerpoCase;


    public Case(){
        this.expr = null;
        this.cuerpoCase = null;
    }

    public Case(Expr expr, ArrayList<Statement> cuerpoCase) {
        this.expr = expr;
        this.cuerpoCase = cuerpoCase;
    }
}
