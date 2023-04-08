package ditto.ast.statements;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.designators.Designator;
import ditto.ast.expressions.Expr;

public class Assign extends Node implements Statement {
    private final Designator place;
    private final Expr expr;

    public Assign(Designator place, Expr expr) {
        this.place = place;
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }

    public Designator getPlace() {
        return place;
    }

    @Override
    public String getAstString() { return "assing"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(place, expr); }
}
