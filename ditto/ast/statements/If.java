package ditto.ast.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.expressions.Expr;

public class If extends Node implements Statement {
    private Expr cond;
    private ArrayList<Statement> then;
    private ArrayList<Statement> els;

    public If(Expr cond, ArrayList<Statement> then) {
        this.cond = cond;
        this.then = then;
        this.els =  new ArrayList<Statement>();
    }

    public If(Expr cond, List<Statement> then, List<Statement> els) {
        this.cond = cond;
        this.then = (ArrayList) then;
        this.els = (ArrayList) els;
    }


    @Override
    public String getAstString() { return "if"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(cond, then, els); }
}
