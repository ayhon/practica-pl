package ditto.ast;

import java.util.ArrayList;

public class While implements Node {
    public final Expr cond;
    public final ArrayList<Statement> statements;

    While(Expr cond, ArrayList<Statement> statements) {
        this.cond = cond;
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "(while " + cond + " " + statements + ")";
    }
}
