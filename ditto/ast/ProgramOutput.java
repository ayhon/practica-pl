package ditto.ast;

public class ProgramOutput {
    /// Clase que va encadenando las instrucciones de salida
    private final StringBuilder sb = new StringBuilder();

    public ProgramOutput() {
    }

    public void append(String s) {
        sb.append(s);
    }

    @Override
    public String toString() {
        return this.sb.toString();
    }
}
