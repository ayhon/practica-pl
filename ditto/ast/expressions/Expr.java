package ditto.ast.expressions;

import ditto.ast.ProgramOutput;
import ditto.ast.statements.Statement;

public abstract class Expr extends Statement {
    /// Todas las expresiones tienen que tener un tipo
    public abstract void compileAsExpr(ProgramOutput out);

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        out.comment("INSTRUCTION: " + this.decompile());
        compileAsExpr(out);
        out.drop();
    }

    public Integer evalIntAtCompileTime() {
        return null;
    }
}
