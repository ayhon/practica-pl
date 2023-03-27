package ditto.ast;

public interface Node {
    // public ?? type() // for the future
    // public ?? bind() // for the future
    // public ?? generateCode() // for the future

    /// public Kind kind();
    public String toString();
}
