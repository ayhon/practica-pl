package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.literals.ArrayLiteral;
import ditto.ast.literals.Literal;
import ditto.ast.literals.StructLiteral;
import ditto.ast.types.Type;

public class New extends Node implements Expr {
    public Literal value;

    public New(StructLiteral val) {
        this.value = val;
    }

    public New(ArrayLiteral val) {
        this.value = val;
    }

    @Override
    public Type getType() {
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    @Override
    public String getAstString() { return "new"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(value); }
}
