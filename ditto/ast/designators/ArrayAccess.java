package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.ArrayType;
import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

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
        /// Nota: el typecheck() ya se encarga de hacer comprobaciones
        /// Deberia haber sido llamado antes de llamar a este metodo
        Type arr_type = array.type();
        return ((ArrayType) arr_type).getElementType();
    }

    @Override
    public void typecheck() {
        if (!index.type().equals(IntegerType.getInstance())) {
            throw new TypeError(String.format("Cannot index with non-integer type '%s'", index.type()));
        }

        Type arr_type = array.type();
        if (!(arr_type instanceof ArrayType)) {
            throw new TypeError(String.format("Cannot index a non-array type '%s'", arr_type));
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
}
