package ditto.ast.expresions;

import java.util.ArrayList;

import ditto.ast.Node;

public class Call implements Node {
    private final String id;
    private final ArrayList<Expr> args;

    Call(String id, ArrayList<Expr> args) {
        this.id = id;
        this.args = args;
    }

    public ArrayList<Expr> getArgs() {
        return args;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "(call " + id + " " + args + ")";
    }
}
