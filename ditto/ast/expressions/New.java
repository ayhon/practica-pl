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
        // Llama a la funcion que reserva el espacio necesario
        // y deja en la cima la dirección de inicio de ese espacio.
        this.allocHeap(out);
    }

    // Reserva el tamaño necesario en el heap y devuelve la dirección de inicio
    // en la cima de la pila
    private void allocHeap(ProgramOutput out) {
        Integer size = value.type().size();
        out.i32_const(size);
        out.call(ProgramOutput.RESERVE_HEAP);
        out.get_global("NP"); // Esta es la dirección donde empieza el nuevo array
    }
}
