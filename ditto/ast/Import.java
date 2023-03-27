package ditto.ast;

public class Import implements Node {
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
