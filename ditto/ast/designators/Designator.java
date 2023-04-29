package ditto.ast.designators;

import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;

public abstract class Designator extends Expr {
    public abstract void compileAsDesig(ProgramOutput out);

    @Override
    public void compileAsExpr(ProgramOutput out) {
        compileAsDesig(out);
        out.i32_load();
    }
}