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
        expr.compileAsExpr(out);

        /// Hacer un return explicito, porque sino, en casos como return dentro de un
        /// if, se raya WebAssembly y piensa que estas dejando valor dentro del bloque if
        out.doReturn();
    }
}
