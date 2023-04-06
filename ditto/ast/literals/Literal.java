package ditto.ast.literals;

import ditto.ast.expressions.Expr;

public interface Literal extends Expr {
    /// Todas las literales tienen que tener un valor
    public Object getValue();
}
