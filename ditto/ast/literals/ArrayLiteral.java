package ditto.ast.literals;

import java.util.ArrayList;
import java.util.Arrays;

import ditto.ast.Node;
import ditto.ast.types.ArrayType;
import ditto.ast.types.Type;

public class ArrayLiteral extends Node implements Literal {
    private final ArrayList<Literal> elements;
    private final Type elem_type;
    private final ArrayList<Object> ast_args;
    public ArrayList<Literal> getElements() {
        return elements;
    }
    public ArrayLiteral(ArrayList<Literal> elements) {
        this.elements = elements;
        this.ast_args = new ArrayList<Object>();
        for(Literal l : elements) {
            ast_args.add(l);
        }
        if (elements.size() > 0) {
            this.elem_type = elements.get(0).getType();
        } else {
            this.elem_type = null;
        }
    }
    public ArrayLiteral() {
        this(new ArrayList<Literal>());
    }
    public ArrayLiteral(Literal fill, int size) {
        this(new ArrayList<Literal>(size));
        for (int i = 0; i < size; i++) {
            elements.set(i, fill);
        }
    }

    @Override
    public String getAstString() { return "arr"; }

    @Override
    public ArrayList<Object> getAstArguments() { 
        return ast_args;
    }
    @Override
    public Type getType() { return new ArrayType(elem_type); }

}
