package ditto.ast.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.GlobalContext;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefFunc;
import ditto.ast.definitions.Definition;
import ditto.ast.designators.Designator;
import ditto.ast.designators.StructAccess;
import ditto.ast.designators.Var;
import ditto.ast.types.FuncType;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class Call extends Expr {
    private final Designator func;
    private final List<Expr> args;
    private DefFunc funcDef;

    public Call(Designator func) {
        this.func = func;
        this.args = new ArrayList<>(0);
    }

    public Call(Designator func, List<Expr> args) {
        this.func = func;
        this.args = args;
    }

    @Override
    public String getAstString() {
        return "call";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(func, args);
    }

    public List<Expr> getArgs() {
        return args;
    }

    public Designator getFunc() {
        return func;
    }

    @Override
    public void bind(GlobalContext global, LocalContext local) {
        super.bind(global, local);

        this.funcDef = this.func.getDefinition();

        if (func instanceof Var) {
            /// MAL, DEBERIA DEVOLVER FUNC.GETDEFINTION DIRECTAMENTE
            Var varFunc = (Var) func;
            this.funcDef = global.getFunction(varFunc);
        } else if (func instanceof StructAccess) {
            /// Necesito saber la definicion de esta funcion
            /// Tengo un designador de forma StructAccess, varStruct.metodo
            /// Seria muy comodo si tengo un metodo que sea getDefinition en StructAccess,
            /// que me deja obtener su definicion directo

            this.funcDef = (DefFunc) this.func.getDefinition();
        } else {
            throw new TypeError(String.format("'%s' is not a function", this.func));
        }

        if (this.funcDef == null) {
            throw new TypeError(String.format("'%s' is not a function", this.func));
        }
    }

    @Override
    public Type type() {
        return funcDef.getResult();
    }

    @Override
    public void typecheck() {
        /// Hay que comprobar que el designator sea una funcion
        this.func.typecheck();
        if (!(this.func.type() instanceof FuncType)) {
            throw new TypeError(String.format("'%s' is not a function", this.func));
        }
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(func);
        children.addAll(args);
        return children;
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsExpr'");
    }
}
