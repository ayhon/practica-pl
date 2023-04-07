package ditto.ast.statements;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.expressions.Expr;

public class If implements Statement {
    private Expr cond;
    private ArrayList<Statement> then;
    private ArrayList<Statement> els;

    public If(Expr cond, ArrayList<Statement> then) {
        this.cond = cond;
        this.then = then;
        this.els =  new ArrayList<Statement>();
    }

    public If(Expr cond, List<Statement> then, List<Statement> els) {
        this.cond = cond;
        this.then = (ArrayList) then;
        this.els = (ArrayList) els;
    }


    @Override
    public String toString() {
        return "(if " + cond + " " + then + " " + els + ")";
    }
}
