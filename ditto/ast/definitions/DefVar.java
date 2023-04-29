package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.List;

import ditto.ast.Module;
import ditto.ast.LocalContext;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;

public class DefVar extends Definition {
    private final String iden;
    private final Expr expr;
    private final Type type;
    private int position;

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

    public Type getType(){
        // Como type(), pero getType() se puede usar antes del type-checking
        return type;
    }

    @Override
    public Type type() {
        return getType();
    }

    @Override
    public String getIden() {
        return iden;
    }

    @Override
    public void bind(Module global, LocalContext local) {
        if (local != null) {
            local.addDef(this);
        } else {
            // Es una variable global, ya está en el módulo
        }
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        if (this.expr != null)
            children.add(expr);
        return children;
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }

    @Override
    public int computeDelta(int lastPosition){
        this.position = lastPosition;
        lastPosition += this.type.size();
        return lastPosition;
    }

    @Override
    public int computeMaxFuncSize(){
        return this.type.size();
    }

    public int getDelta(){
        return this.position;
    }

}
