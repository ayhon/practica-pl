package ditto.ast;

public class Param {
    private final Type type;
    private final String name;

    Param(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
