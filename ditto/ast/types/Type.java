package ditto.ast.types;

public interface Type {
    /// Lo que ocuparía en memoria. Se usa para calcular delta.
    public int size();
}
