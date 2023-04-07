package ditto.ast.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.expressions.Expr;

public class If extends Node implements Statement {
    public final Expr cond;
    public final ArrayList<Statement> then;
    public final ArrayList<Statement> els;

    public If(Expr cond, ArrayList<Statement> then) {
        this(cond, then, new ArrayList<Statement>());
    }

    public If(Expr cond, ArrayList<Statement> then, ArrayList<Statement> els) {
        this.cond = cond;
        this.then = then;
        this.els = els;
    }

    @Override
    public String getAstString() { return "if"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(cond, then, els); }
}
