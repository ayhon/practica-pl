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
        /// Reservar espacio en heap
        out.i32_const(this.value.type().size());
        out.call(ProgramOutput.RESERVE_HEAP);

        /// Guardar dirección del inicio del array al final de pila, para devolver
        /// despues
        out.get_global("NP");
        out.duplicate();

        this.value.compileAsAssign(out);
    }
}
