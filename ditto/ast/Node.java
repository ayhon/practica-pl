package ditto.ast;

import java.util.List;
import java.util.StringJoiner;

public abstract class Node {
    // public ?? type() // for the future
    // public ?? bind() // for the future
    // public ?? generateCode() // for the future
    public abstract String getAstString();

    public abstract List<Object> getAstArguments();

    @Override
    @SuppressWarnings("unchecked")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(getAstString());
        sb.append("\n");

        StringJoiner args = new StringJoiner(",\n");
        for (Object arg : getAstArguments()) {
            if (arg instanceof String) {
                args.add('"' + (String) arg + '"');
            } else if (arg instanceof List) {
                args.add(listAsString((List<Object>) arg));
            } else {
                args.add(arg.toString());
            }
        }
        sb.append(args.toString().indent(4));
        sb.append(')');

        return sb.toString();
    }

    private static String listAsString(List<Object> list) {
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
