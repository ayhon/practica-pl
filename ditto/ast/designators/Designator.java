package ditto.ast.designators;

import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;

public abstract class Designator extends Expr {
    public abstract void compileAsDesig(ProgramOutput out);

    @Override
    public final void compileAsExpr(ProgramOutput out) {
        compileAsDesig(out);

        /// Tengo que copiar los elementos que apunta el puntero al cima de la pila
        /// Direccion inicial de memoria: compileAsDesig(out)
        /// Tamaño del tipo: type().size()
        /// Quiero copiar desde compileAsDesig(out) type().size() posiciones
        out.mem_read(type().size());
    }
}