package ditto.ast.statements;

public class Import implements Statement {
    private final String id;

    Import(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "(import " + id + ")";
    }
}
