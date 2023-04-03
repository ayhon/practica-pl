package ditto.ast.literals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.ArrayType;
import ditto.ast.types.Type;

public class ArrayLiteral extends Node implements Literal {
    private final List<Literal> elements;
    private final Type elem_type;
    private final List<Object> ast_args;
    public List<Literal> getElements() {
        return elements;
    }
    public ArrayLiteral(List<Literal> elements) {
        this.elements = elements;
        this.ast_args = Arrays.asList(elements);
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
        for(Literal elem : elements){
            elem = fill;
        }
    }

    @Override
    public String getAstString() { return "arr"; }

    @Override
    public List<Object> getAstArguments() { 
        return elements; 
    }
    @Override
    public Type getType() { return new ArrayType(elem_type); }

}
