package ditto.ast.expressions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.literals.ArrayLiteral;
import ditto.ast.literals.Literal;
import ditto.ast.literals.StructLiteral;
import ditto.ast.types.PointerType;

public class New extends Expr {
    public Literal value;

    public New(StructLiteral val) {
        this.value = val;
    }

    public New(ArrayLiteral val) {
        this.value = val;
    }

    @Override
    public String getAstString() {
        return "new";
    }

    @Override
    public void typecheck() {
        super.typecheck();
        this.type = new PointerType(value.type());
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(value);
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(value);
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        /// Basta con llamar compileAsExpr de ArrayLiteral / StructLiteral, porque
        /// devuelve ya su dirección en heap
        this.value.compileAsExpr(out);
    }
}
