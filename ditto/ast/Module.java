package ditto.ast;

import java.util.ArrayList;

public class Module implements Node {
    private final String id;
    private final ArrayList<Statement> statements;

    Module(String id, ArrayList<Statement> statements) {
        this.id = id;
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "(module " + id + " " + statements + ")";
    }

    public String getId() {
        return id;
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }
}
