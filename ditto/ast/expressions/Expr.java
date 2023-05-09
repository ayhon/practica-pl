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

        /// Si la expresión es tipo básico, entonces con un drop ya está
        /// Y si no es básico, entonces compileAsExpr devuelve la dirección de memoria
        /// inicial -> También con un drop vale
        out.drop();
    }

    public void compileAsAssign(ProgramOutput out) {
        /// Recibe una direccion encima de la pila, y guarda el resultado directamente
        /// alli
        /// Es util para tipos no basicos
        if (this.type().isBasic) {
            compileAsExpr(out);
            out.i32_store();
        } else {
            throw new RuntimeException("Los no basicos tiene que sobreescribir este metodo");
        }
    }

    public Integer evalIntAtCompileTime() {
        return null;
    }
}
