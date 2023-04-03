package ditto.ast;

import java.util.ArrayList;

public class Struct {
    private String id;
    private ArrayList<Var> vars;
    private ArrayList<Func> funs;

    Struct(String id, ArrayList<Var> vars, ArrayList<Func> funs) {
        this.id = id;
        this.vars = vars;
        this.funs = funs;
    }

    public ArrayList<Func> getFuns() {
        return funs;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Var> getVars() {
        return vars;
    }

    @Override
    public String toString() {
        return "(struct " + id + " " + vars + " " + funs + ")";
    }
}
