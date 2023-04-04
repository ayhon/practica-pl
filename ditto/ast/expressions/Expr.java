package ditto.ast.expressions;

import ditto.ast.types.Type;

public interface Expr {
    /// Todas las expresiones tienen que tener un tipo
    public Type getType();
}
