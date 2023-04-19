package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;

public class DefVar extends Definition {
    private final String iden;
    public String getIden() {
        return iden;
    }
    private final Expr expr;
    private final Type type;

    public DefVar(Type type, String iden, Expr expr) {
        // Argumentos en este orden para representar como se escribe en el lenguaje
        this.iden = iden;
        this.type = type;
        this.expr = expr;
    }

    public DefVar(Type type, String name) {
        this(type, name, null);
    }

    @Override
    public String getAstString() {
        return "let";
    }

    @Override
    public List<Object> getAstArguments() {
        List<Object> args = new ArrayList<Object>();
        args.add(type);
        args.add(iden);
        if (this.expr != null)
            args.add(expr);
        return args;
    }

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
