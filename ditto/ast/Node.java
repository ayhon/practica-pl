package ditto.ast;

import java.util.List;
import java.util.StringJoiner;

import ditto.ast.types.Type;

public abstract class Node {
    /*
    type LocalScopes = Map<String, (DefVar | DefFunc | DefStruct | DefModule)>;
    type Context = List<LocalScopes>;
    class Context {
        GlobalScope globalScope,
        List<LocalScopes> localScopes;
        List<List<LocalScopes>> otros;
        public getDef(String id);
        public enterFunction();
    } 
    void bind(gl, scs){
        for (Statement inst : this.body)
            inst.bind(gl, new ArrayList<>());
    }
    */

    // Vincularmos cada uso de una definición con su definición
    public void bind(GlobalContext global, LocalContext local) {
        for (Node child : getAstChildren()) 
            child.bind(global, local);
    }
    
    public abstract Type type(); // Desde Module se llama antes a `bind`. El árbol debe estar vinculado paque se pueda tipar
    // Te devuelve el tipo de expreisiones
    // si se aplica a un nodo que no es una expresión, devuelve Null.getInstance()
    
    public abstract void generateCode(ProgramOutput out); // Desde Module llama a `type` antes de recursar,
    // type ProgramOutput = StringBuilder;
    
    public abstract String getAstString();
    public abstract List<Object> getAstArguments();
    public abstract List<Node> getAstChildren();

    @Override
    @SuppressWarnings("unchecked")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(getAstString());
        sb.append("\n");

        StringJoiner args = new StringJoiner(",\n");
        for (Node arg : getAstChildren()) {
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
