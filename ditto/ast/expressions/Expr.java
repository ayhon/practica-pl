package ditto.ast.expressions;

import ditto.ast.ProgramOutput;
import ditto.ast.statements.Statement;

public abstract class Expr extends Statement {
    /// Todas las expresiones tienen que tener un tipo
    public abstract void compileAsExpr(ProgramOutput out);

    @Override
    public void compile(ProgramOutput out) {
        compileAsExpr(out);
        out.drop();
    }
}
