package ditto.ast.literals;

import java.util.ArrayList;
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

        this.ast_args = new ArrayList<Object>();
        for (Literal l : elements) { // ast_args es elements pero como List<Object> en vez de List<Literal>
            ast_args.add(l);
        }

        if (elements.size() > 0) {
            this.elem_type = elements.get(0).getType();
        } else {
            this.elem_type = null; // What do we do with the type of an empty array? If they can't be resized, it's weird to even keep.
        }
    }

    public ArrayLiteral() {
        this(new ArrayList<Literal>());
    }

    public ArrayLiteral(Literal fill, Natural size) {
        // TODO: Probablemente podamos reutilizar constructores de alguna manera
        this.elements = new ArrayList<Literal>(size.getValue());
        for (int i = 0; i < size.getValue(); i++) {
            elements.add(fill);
        }
        this.ast_args = new ArrayList<Object>();
        for (Literal l : elements) { // ast_args es elements pero como List<Object> en vez de List<Literal>
            ast_args.add(l);
        }

        if (elements.size() > 0) {
            this.elem_type = elements.get(0).getType();
        } else {
            this.elem_type = null; // What do we do with the type of an empty array? If they can't be resized, it's weird to even keep.
        }
    }

    @Override
    public String getAstString() {
        return "arr";
    }

    @Override
    public List<Object> getAstArguments() {
        return ast_args;
    }

    @Override
    public Type getType() {
        return new ArrayType(elem_type);
    }

}
