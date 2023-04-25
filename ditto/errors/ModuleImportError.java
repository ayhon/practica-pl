package ditto.errors;

public class ModuleImportError extends RuntimeException {
    public ModuleImportError(String message) {
        super(message);
    }
}
