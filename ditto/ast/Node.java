package ditto.ast;

import java.util.List;

public abstract class Node {
    // public ?? type() // for the future
    // public ?? bind() // for the future
    // public ?? generateCode() // for the future
    public abstract String getAstString();
    public abstract List<Object> getAstArguments();

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(getAstString());
        for (Object arg : getAstArguments()) {
            sb.append(' ');
            if (arg instanceof String) {
                sb.append('"');
                sb.append(arg);
                sb.append('"');
            } else if (arg instanceof List) {
                sb.append(listAsString((List<Object>) arg));
            } else {
                sb.append(arg.toString());
            }
        }
        sb.append(')');
        return sb.toString();
    }

    private static String listAsString(List<Object> list) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int idx = 0;
        for (Object item : list) {
            sb.append(item.toString());
            if(idx != list.size() - 1) 
                sb.append(',');
            idx += 1;
        }
        sb.append(']');
        return sb.toString();
    }
}

