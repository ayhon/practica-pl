package ditto.errors;

public class SemanticError extends RuntimeException {
    public SemanticError(String message) {
        super("[Semantic Error] " + message);
    }
}