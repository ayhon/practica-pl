package ditto.ast;

import java.util.List;
import java.util.StringJoiner;

import ditto.ast.types.Type;

public abstract class Node implements Bindable {
    /*
     * type LocalScopes = Map<String, (DefVar | DefFunc | DefStruct | DefModule)>;
     * type Context = List<LocalScopes>;
     * class Context {
     * GlobalScope globalScope,
     * List<LocalScopes> localScopes;
     * List<List<LocalScopes>> otros;
     * public getDef(String id);
     * public enterFunction();
     * }
     * void bind(gl, scs){
     * for (Statement inst : this.body)
     * inst.bind(gl, new ArrayList<>());
     * }
     */

    public List<Bindable> getBindableChildren() {
        return getAstChildren().stream().map(x -> (Bindable) x).toList();
    }

    // Vincularmos cada uso de una definición con su definición
    public void bind(Module global, LocalContext local) {
        for (Node child : getAstChildren()) {
            // System.out.println("Node.bind: " + child);
            child.bind(global, local);
        }
    }

    public abstract Type type();

    public void typecheck() {
        /*
         * Llamar al typecheck de nodos hijos para que comprueben sus tipos
         * Nota: antes de llamar a este método, se debe haber llamado a `bind` para
         * vincular
         */
        for (Node child : getAstChildren()) {
            child.typecheck();
        }
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

    public abstract void compile(ProgramOutput out); // Desde Module llama a `type` antes de recursar,
    // type ProgramOutput = StringBuilder;

    public abstract String getAstString();

    public abstract List<Object> getAstArguments();

    public abstract List<Node> getAstChildren();

    @Override
    @SuppressWarnings("unchecked")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(getAstString() /* + ": " + type() */); // No podemos llamar a type() porque no hemos hecho el binding
                                                         // todavia
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
