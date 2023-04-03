package ditto.ast;

import java.util.ArrayList;

public class Func implements Node {
    private final String id;
    private final ArrayList<Param> params;
    private final Type result;
    private final ArrayList<Statement> body;

    Func(String id, ArrayList<Param> params, Type result, ArrayList<Statement> body) {
        this.id = id;
        this.params = params;
        this.result = result;
        this.body = body;
    }

    @Override
    public String toString() {
        return "(fun " + id + " " + params + " " + result + " " + body + ")";
    }
}
