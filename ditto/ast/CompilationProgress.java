package ditto.ast;

public enum CompilationProgress {
    NONE,
    BIND,
    CALCULATING_TYPE_SIZE,
    TYPE_SIZE, // Para detectar loops en calculo de size
    TYPE,
    FUNC_SIZE_AND_DELTAS,
    COMPILE;

    public boolean lessThan(CompilationProgress other) {
        return this.ordinal() < other.ordinal();
    }

    public boolean atLeast(CompilationProgress other) {
        return !(this.lessThan(other));
    }
}
