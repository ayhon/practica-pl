package ditto.ast.statements;

import java.util.List;
import java.util.Arrays;

import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;

public class While extends Statement {
    public final Expr cond;
    public final List<Statement> statements;

    public While(Expr cond, List<Statement> statements) {
        this.cond = cond;
        this.statements = statements;
    }

    @Override
    public String getAstString() { return "while"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(cond, statements); }

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
}
