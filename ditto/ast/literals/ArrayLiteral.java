package ditto.ast.literals;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.ArrayType;
import ditto.ast.types.Type;

public class ArrayLiteral extends Node implements Literal {
    private final List<Literal> elements;
    private final ArrayType array_type;
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

        this.array_type = new ArrayType(elements.get(0).getType(), new Natural(elements.size()));
    }

    public ArrayLiteral() {
        this(new ArrayList<Literal>());
    }

    public ArrayLiteral(Literal fill, Natural size) {
        int length = (int) size.getValue();
        this.elements = new ArrayList<Literal>(length);

        for (int i = 0; i < length; i++) {
            elements.add(fill);         //El valor por defecto es siempre el mismo
        }

        this.ast_args = new ArrayList<Object>();
        
        for (Literal l : elements) { // ast_args es elements pero como List<Object> en vez de List<Literal>
            ast_args.add(l);
        }

        this.array_type = new ArrayType(elements.get(0).getType(), new Natural(elements.size()));
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
        return this.array_type;
    }

    @Override
    public Object getValue() {
        return this.elements;
    }

}
