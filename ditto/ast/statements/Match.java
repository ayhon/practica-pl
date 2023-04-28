package ditto.ast.statements;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;

public class Match extends Statement {
    private Expr expr;
    private List<Case> cases;
    private List<Statement> otherwise;

    private Type type = null;

    public Match(Expr expr, List<Case> cases) {
        this.expr = expr;
        this.cases = cases;
        this.otherwise = new ArrayList<Statement>();
    }

    public Match(Expr expr, List<Case> cases, List<Statement> otherwise) {
        this.expr = expr;
        this.cases = cases;
        this.otherwise = otherwise;
    }

    static public class Case extends Node {
        public final Expr expr;
        public final List<Statement> body;

        private Type type = null;

        public Case(Expr expr, List<Statement> body) {
            this.expr = expr;
            this.body = body;
        }

        @Override
        public String getAstString() {
            return "case";
        }

        @Override
        public List<Object> getAstArguments() {
            return Arrays.asList(expr, body);
        }

        @Override
        public Type type() {
            this.type = expr.type();
            return this.type;
        }

        @Override
        public List<Node> getAstChildren() {
            List<Node> children = new ArrayList<Node>();
            children.add(expr);
            children.addAll(body);
            return children;
        }

        @Override
        public void compile(ProgramOutput out) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'compile'");
        }

    }

    @Override
    public String getAstString() {
        return "match";
    }

    @Override
    public List<Object> getAstArguments() {
        if (otherwise.size() > 0)
            return Arrays.asList(expr, cases, otherwise);
        else
            return Arrays.asList(expr, cases);
    }

    @Override
    public Type type() {
        Type aux = expr.type();
        for(Case c : this.cases){
            if(!aux.equals(c.expr.type())){
                throw new RuntimeException("Type mismatch in case");
            }
        }
        this.type = aux;
        return this.type;
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(expr);
        children.addAll(cases);
        children.addAll(otherwise);
        return children;
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }
}
