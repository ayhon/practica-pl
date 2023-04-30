package ditto.ast.statements;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import ditto.ast.Delta;
import ditto.ast.Context;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;

import ditto.ast.types.Type;
import ditto.ast.types.VoidType;
import ditto.errors.TypeError;

public class Match extends Statement {
    private Expr expr;
    private List<Case> cases;

    public Match(Expr expr, List<Case> cases) {
        this.expr = expr;
        this.cases = cases;
    }

    public Match(Expr expr, List<Case> cases, List<Statement> otherwise) {
        this.expr = expr;
        this.cases = cases;
        this.cases.add(new Case(otherwise));
    }

    static public class Case extends Statement {
        public final Expr expr;
        public final List<Statement> body;
        /// Otherwise case
        public Case(List<Statement> body) {
            this(null, body);
        }

        public Case(Expr expr, List<Statement> body) {
            this.expr = expr;
            this.body = body;
        }

        @Override
        public String getAstString() {
            if (expr == null)
                return "otherwise";
            else
                return "case";
        }

        @Override
        public List<Object> getAstArguments() {
            return Arrays.asList(expr, body);
        }

        @Override
        public List<Node> getAstChildren() {
            List<Node> children = new ArrayList<Node>();
            if (expr != null)
                children.add(expr);
            children.addAll(body);
            return children;
        }

        @Override
        public void bind(Context ctx) {
            ctx.pushScope();
            super.bind(ctx);
            ctx.popScope();
        }

        @Override
        public void computeOffset(Delta delta) {
            delta.enterBlock();
            super.computeOffset(delta);
            delta.exitBlock();
        }

        @Override
        public void compile(ProgramOutput out) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'compile'");
        }

        @Override
        public void compileAsInstruction(ProgramOutput out) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
        }

    }

    @Override
    public String getAstString() {
        return "match";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(expr, cases);
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(expr);
        children.addAll(cases);
        return children;
    }

    @Override
    public Type type() {
        return VoidType.getInstance();
    }

    @Override
    public void typecheck() {
        super.typecheck();
        Type matchingType = expr.type();
        
        for (Case c : this.cases) {
            if (c.expr != null && !matchingType.equals(c.expr.type())) {
                throw new TypeError("Type mismatch in case");
            }
        }   
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }

}
