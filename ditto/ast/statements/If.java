package ditto.ast.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;

public class If extends Statement {
    private Expr cond;
    private List<Statement> then;
    private List<Statement> els;

    public If(Expr cond, List<Statement> then) {
        this.cond = cond;
        this.then = then;
        this.els  = new ArrayList<Statement>();
    }

    public If(Expr cond, List<Statement> then, List<Statement> els) {
        this.cond = cond;
        this.then = then;
        this.els  = els;
    }


    @Override
    public String getAstString() { return "if"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(cond, then, els); }

    @Override
    public Type type() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'type'");
    }

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(cond);
        children.addAll(then);
        children.addAll(els);
        return children;
    }
}
