package ditto.ast.statements;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.designators.Designator;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class Assign extends Statement {
    private final Designator place;
    private final Expr expr;

    public Assign(Designator place, Expr expr) {
        this.place = place;
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(place, expr);
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
    public void typecheck() {
        super.typecheck();
        Type placeType = place.type();
        if (!placeType.equals(expr.type())) {
            throw new TypeError(
                    String.format("Can't assign %s to variable %s of type %s", expr.type(), place, placeType));
        }
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        place.compileAsDesig(out);
        expr.compileAsExpr(out);
        out.i32_store();
    }
}
