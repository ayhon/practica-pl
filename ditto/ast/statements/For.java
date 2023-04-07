package ditto.ast.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.expressions.Expr;
import ditto.ast.literals.Natural;

public class For extends Node implements Statement {
    public final String index;
    public final Expr from;
    public final Expr to;
    public final Expr by;
    public final ArrayList<Statement> body;

    public For(String index, Expr from, Expr to, Expr by, ArrayList<Statement> body) {
        this.index = index;
        this.from = from;
        this.to = to;
        this.by = by;
        this.body = body;
    }

    public For(String index, Expr from, Expr to, ArrayList<Statement> body) {
        this(index, from, to, new Natural(1), body);
    }

    @Override
    public String getAstString() { return "for"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(index, from, to, by, body); }

}
