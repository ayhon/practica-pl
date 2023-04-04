package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.expressions.Expr;
import ditto.ast.types.ArrayType;
import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;

public class ArrayAccess extends Node implements Designator {
    private final Designator array;
    private final Expr index;

    public ArrayAccess(Designator array, Expr index) {
        this.array = array;
        this.index = index;

        /* Para cuando implementamos el chequeo de tipos

        */
    }

    public Designator getArray() {
        return array;
    }

    public Expr getIndex() {
        return index;
    }

    @Override
    public Type getType() {
        if (index.getType() != IntegerType.getInstance()) { // Podemos usar != pues es un singleton
            throw new IllegalArgumentException("Array index must be an integer");
        }
        Type arr_type = array.getType();
        if (arr_type instanceof ArrayType) { // Podemos usar == pues es un singleton
            return ((ArrayType) arr_type).getElementType();
        } else throw new IllegalArgumentException("Cannot index a non-array type");
    }

    @Override
    public String getAstString() {
        return "index";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(array, index);
    }

}
