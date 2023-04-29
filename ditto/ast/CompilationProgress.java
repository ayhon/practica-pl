package ditto.ast;

public enum CompilationProgress {
    NONE,
    BIND,
    TYPE,
    SIZE, // Para detectar loops en calculo de size
    DELTAS,
    COMPILE;

    public boolean lessThan(CompilationProgress other) {
        return this.ordinal() < other.ordinal();
    }

    public boolean atLeast(CompilationProgress other) {
        return !(this.lessThan(other));
    }
}