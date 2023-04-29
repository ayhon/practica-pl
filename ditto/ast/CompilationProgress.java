package ditto.ast;

public enum CompilationProgress {
    NONE,
    BIND,
    TYPE,
    COMPUTING_DELTAS,
    COMPILE;

    public boolean lessThan(CompilationProgress other) {
        return this.ordinal() < other.ordinal();
    }
}