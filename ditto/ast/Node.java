package ditto.ast;

import java.util.List;
import java.util.StringJoiner;

import ditto.ast.types.Type;
import ditto.errors.BindingError;
import ditto.errors.TypeError;

public abstract class Node {
    private CompilationProgress progress = CompilationProgress.NONE;
    protected Type type = null;

    public CompilationProgress getProgress() {
        return progress;
    }

    public void setProgress(CompilationProgress progress) {
        this.progress = progress;
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

    public final Type type() {
        if (this.type == null)
            throw new TypeError(String.format("Can't get type before typechecking of %s", this.getAstString()));
        return this.type;
    }

    public void computeTypeSize() {
        this.progress = CompilationProgress.CALCULATING_TYPE_SIZE;
        for (Node child : getAstChildren()) {
            if (child.getProgress().lessThan(CompilationProgress.CALCULATING_TYPE_SIZE))
                child.computeTypeSize();
            else if(child.getProgress().equals(CompilationProgress.CALCULATING_TYPE_SIZE))
                throw new BindingError(String.format(
                    "Intentamos entrar en un bucle infinito para calcular el tamaño de un tipo. Estabamos en %s[%s] y queriamos entrar en %s[%s]",
                    this,this.getProgress(),
                    child, child.getProgress()
                ));
        }
        this.progress = CompilationProgress.TYPE_SIZE;
    }

    // Calcula para cada definicion de variable su delta. El delta se resetea cuando
    // entramos en bloque. También calculamos el tamaño máximo a reservar en la pila
    // pra funciones.
    public void computeOffset(Delta lastDelta) {
        this.progress = CompilationProgress.FUNC_SIZE_AND_DELTAS;
        for (Node child : getAstChildren()) {
            if (child.getProgress().lessThan(CompilationProgress.FUNC_SIZE_AND_DELTAS))
                child.computeOffset(lastDelta);
        }
    }

    //Este medotodo se encarga de calcular la profundidad de una declaración de variable
    //Cada vez que entramos en una función o struct(cada vez que reseteamos el delta),
    // aumentamos la profundidad en 1. Realizamos varias pasadas, hasta que devuelve falso
    public boolean computeDeep(int currDeep){
        boolean changed = false;
        for(Node child : getAstChildren()){
            changed |= child.computeDeep(currDeep);
        }
        return changed;
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
            if (arg == null)
                continue;
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
