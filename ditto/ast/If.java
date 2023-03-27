package ditto.ast;

import java.util.ArrayList;

public class If implements Node {
    public final Expr cond;
    public final ArrayList<Statement> then;
    public final ArrayList<Statement> els;

    If(Expr cond, ArrayList<Statement> then, ArrayList<Statement> els) {
        this.cond = cond;
        this.then = then;
        this.els = els;
    }

    @Override
    public String toString() {
        return "(if " + cond + " " + then + " " + els + ")";
    }
}
