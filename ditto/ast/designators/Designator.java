package ditto.ast.designators;

import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;

public abstract class Designator extends Expr {
    public abstract void compileAsDesig(ProgramOutput out);

    @Override
    public void compileAsExpr(ProgramOutput out) {
        compileAsDesig(out);
        if (this.type.isBasic) {
            out.i32_load();
        } else {
            throw new RuntimeException("No puedes compilar un tipo no basico como una expresion");
        }
    }

    @Override
    public void compileAsAssign(ProgramOutput out) {
        if (this.type().isBasic) {
            this.compileAsExpr(out);
            out.i32_store();
        } else {
            /// Con copyn
            /// Copia no basica
            out.comment("FROM");
            this.compileAsDesig(out);

            out.comment("TO");
            out.call(ProgramOutput.SWAP);

            out.comment("SIZE");
            out.i32_const(this.type().size() / 4);

            out.call(ProgramOutput.COPYN);
        }
    }
}