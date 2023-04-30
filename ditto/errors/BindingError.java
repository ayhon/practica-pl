package ditto.errors;

public class BindingError extends RuntimeException {
    public BindingError(String message) {
        super(message);
    }
}
