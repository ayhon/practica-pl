package ditto.ast.expressions;

import ditto.ast.statements.Statement;
import ditto.ast.types.Type;

public interface Expr extends Statement {
    /// Todas las expresiones tienen que tener un tipo

    public Type getType();
}
