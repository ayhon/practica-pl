package ditto.ast.statements;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;

public class Return extends Statement {
    private final Expr expr;

    public Return() {
        this.expr = null;
    }

    public Return(Expr expr) {
        this.expr = expr;
    }

    @Override
    public String getAstString() {
        return "return";
    }

    @Override
    public List<Object> getAstArguments() {
        List<Object> args = new ArrayList<>();
        if (expr != null) {
            args.add(expr);
        }
        return args;
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        if (expr != null) {
            children.add(expr);
        }
        return children;
    }

    public Expr getExpr() {
        return expr;
    }

    @Override
    public String decompile() {
        return String.format("return %s;", expr != null ? expr.decompile() : "");
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        out.comment("INSTRUCTION: " + this.decompile());

        out.comment("Cargar la direccion del lugar donde almacenar el resultado de esta llamada de funcion");
        out.get_global("SP");
        out.i32_const(4);
        out.i32_sub();
        out.i32_load();

        if (this.expr.type().isBasic) {
            out.comment("Guardo el valor de retorno de un tipo basico");
        } else {
            out.comment("Guardo el valor de retorno de un tipo no basico");
        }

        expr.compileAsAssign(out);

        out.doReturn();
    }
}
