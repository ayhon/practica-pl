package ditto.ast;

import java.util.List;
import java.util.StringJoiner;

import ditto.ast.types.Type;
import ditto.errors.TypeError;

public abstract class Node {
    private CompilationProgress progress = CompilationProgress.NONE;
    protected Type type = null;

    public CompilationProgress getProgress() {
        return progress;
    }

    public abstract String getAstString();

    public abstract List<Object> getAstArguments();

    public abstract List<Node> getAstChildren();

    // Vincularmos cada uso de una definición con su definición
    public void bind(Context ctx) {
        progress = CompilationProgress.BIND;
        for (Node child : getAstChildren()) {
            if (child.getProgress().lessThan(CompilationProgress.BIND))
                child.bind(ctx);
        }
    }

    public void typecheck() {
        progress = CompilationProgress.TYPE;
        for (Node child : getAstChildren()) {
            if (child.getProgress().lessThan(CompilationProgress.TYPE))
                child.typecheck();
        }
    }

    public Type type() {
        if (this.type == null)
            throw new TypeError("Can't get type before typechekcing");
        return this.type;
    }

    // Calcula para cada definicion de variable su delta. El delta se resetea cuando
    // entramos en bloque.
    public int computeDelta(int lastPosition) {
        for (Node child : getAstChildren()) {
            lastPosition = child.computeDelta(lastPosition);
        }
        return lastPosition;
    }

    // Calcula para cada función el tamaño que habremos de reservar en la pila para
    // parámetros y variables locales.
    public int computeMaxFuncSize() {
        int max = 0;
        for (Node child : getAstChildren()) {
            max = child.computeMaxFuncSize();
        }
        return max;
    }

    public abstract void compile(ProgramOutput out);

    @Override
    @SuppressWarnings("unchecked")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(getAstString() /* + ": " + type() */); // No podemos llamar a type() porque no hemos hecho el binding
                                                         // todavia

        sb.append(" [PROGRESS: " + progress + "]");
        if (this.progress.atLeast(CompilationProgress.TYPE)) {
            sb.append(" [TYPE: " + type() + "]");
        }
        sb.append("\n");

        StringJoiner args = new StringJoiner(",\n");
        /// Nota: aqui es getAstArguments, no getAstChildren
        for (Object arg : getAstArguments()) {
            if (arg instanceof List) {
                args.add(listAsString((List<Node>) arg));
            } else {
                args.add(arg.toString());
            }
        }
        sb.append(args.toString().indent(4));
        sb.append(')');

        return sb.toString();
    }

    private static String listAsString(List<Node> list) {
        if (list.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("\n");

        StringJoiner args = new StringJoiner(",\n");
        for (Object item : list) {
            if (item == null) {
                args.add("NULL");
            } else {
                args.add(item.toString());
            }
        }

        sb.append(args.toString().indent(4));
        sb.append(']');
        return sb.toString();
    }
}
