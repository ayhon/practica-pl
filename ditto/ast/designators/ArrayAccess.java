package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.ArrayType;
import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;
import ditto.errors.SemanticError;

public class ArrayAccess extends Designator {
    private final Designator array;
    private final Expr index;

    public ArrayAccess(Designator array, Expr index) {
        this.array = array;
        this.index = index;
    }

    public Designator getArray() {
        return array;
    }

    public Expr getIndex() {
        return index;
    }

    @Override
    public Type type() {
        if (!index.type().equals(IntegerType.getInstance())) {
            throw new SemanticError("Array index must be an integer");
        }

        Type arr_type = array.type();
        if (arr_type instanceof ArrayType) {
            return ((ArrayType) arr_type).getElementType();
        } else {
            throw new SemanticError("Cannot index a non-array type");
        }
    }

    @Override
    public String getAstString() {
        return "index";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(array, index);
    }

    @Override
    public void compileAsDesig(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsDesig'");
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(array, index);
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }

}
