package ditto.ast.expressions;

import ditto.ast.ProgramOutput;
import ditto.ast.statements.Statement;
import ditto.ast.types.VoidType;

public abstract class Expr extends Statement {
    /// Todas las expresiones tienen que tener un tipo
    public abstract void compileAsExpr(ProgramOutput out);

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        compileAsExpr(out);

        /// Si no es de tipo void, hacer drop de sus resultados
        /// TODO: Si el resultado tiene mas de un valor, hacer drop de todos
        if (!this.type.equals(VoidType.getInstance())) {
            out.drop();
        }
    }

    public Integer evalIntAtCompileTime() {
        return null;
    }
}
