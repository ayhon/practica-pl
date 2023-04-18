package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.ProgramOutput;
import ditto.ast.literals.ArrayLiteral;
import ditto.ast.literals.Literal;
import ditto.ast.literals.StructLiteral;
import ditto.ast.types.PointerType;
import ditto.ast.types.Type;

public class New extends Expr {
    public Literal value;

    public New(StructLiteral val) {
        this.value = val;
    }

    public New(ArrayLiteral val) {
        this.value = val;
    }


    @Override
    public String getAstString() { return "new"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(value); }

    @Override
    public Type type() {
        return new PointerType(value.type());
    }

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }
}
