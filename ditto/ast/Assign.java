package ditto.ast;

import java.util.List;

import ditto.ast.designators.Designator;
import ditto.ast.expressions.Expr;

public class Assign extends Node {
    private final Designator place;
    private final Expr expr;

    Assign(Designator place, Expr expr) {
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
    public String toString() {
        return "(assign " + place + " " + expr + ")";
    }

    @Override
    public String getAstString() { return ":="; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(place, expr);}
}
