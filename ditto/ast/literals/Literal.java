package ditto.ast.literals;

import ditto.ast.expressions.Expr;

public abstract class Literal extends Expr {
    /// Todas las literales tienen que tener un valor
    public abstract Object getValue();
}
