package ditto.ast.statements;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.designators.Designator;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;

public class Assign extends Statement {
    private final Designator place;
    private final Expr expr;

    private Type type = null;

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
    public String getAstString() {
        return "assign";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(place, expr);
    }

    @Override
    public Type type() {
        Type aux = place.type();
        if(!aux.equals(expr.type())){
            throw new RuntimeException("Type mismatch in assignment");
        }
        this.type = aux;
        return this.type;
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(place, expr);
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        place.compileAsDesig(out);
        expr.compileAsExpr(out);
        out.i32_store();
    }
}
