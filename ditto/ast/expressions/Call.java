package ditto.ast.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefFunc;
import ditto.ast.definitions.Definition;
import ditto.ast.designators.Designator;
import ditto.ast.designators.Name;
import ditto.ast.designators.StructAccess;
import ditto.ast.types.FuncType;
import ditto.errors.TypeError;

public class Call extends Expr {
    private final Designator func;
    private final List<Expr> args;
    private DefFunc funcDef;

    public Call(Designator func) {
        this.func = func;
        this.args = new ArrayList<>();
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
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(func);
        children.addAll(args);
        return children;
    }

    @Override
    public void typecheck() {
        /// Hay que comprobar que el designator sea una funcion
        super.typecheck();
        if (!(this.func.type() instanceof FuncType)) {
            throw new TypeError(String.format("'%s' is not a function", this.func));
        }
        FuncType funcType = (FuncType) this.func.type();
        int expected_args = funcType.getNumberArgs();
        if (expected_args != args.size())
            throw new TypeError(String.format("'%s' expects %d arguments, but %d were given", this.func, expected_args,
                    args.size()));

        this.type = funcType.getReturnType();
        loadFunctionDefinition();

        /// Hay que comprobar que aquellos parametros por referencia son designators
        /// Sino no tienen direccion de memoria
        for (int i = 0; i < args.size(); ++i) {
            var param = funcDef.getParams().get(i);
            if (param.isRef() && !(args.get(i) instanceof Designator)) {
                throw new TypeError(String.format("'%s' is a reference parameter, but '%s' is not a designator",
                        param.getIden(), args.get(i).decompile()));
            }
        }
    }

    private void loadFunctionDefinition() {
        // Sacamos el DefFunc de la función a la que estamos llamando
        Definition def = null;
        if (this.func instanceof StructAccess) { // s.iden(hello);
            StructAccess sa = (StructAccess) this.func;
            def = sa.getDefinition(sa.getField());
        } else if (this.func instanceof Name) { // iden(hello);
            Name n = (Name) this.func;
            def = n.getDefinition();
        } else {
            // No debería darse si se hace el typecheck bien
            throw new TypeError(String.format("'%s' is not a function", this.func));
        }
        if (def == null) {
            throw new TypeError(String.format("'%s' is not a function", this.func));
        }
        if (!(def instanceof DefFunc)) {
            throw new TypeError(String.format("'%s' points to '%s' which is not a function", this.func, def));
        }

        funcDef = (DefFunc) def;
    }

    @Override
    public String decompile() {
        return String.format("%s(%s)", func.decompile(),
                String.join(", ", args.stream().map(Expr::decompile).toArray(String[]::new)));
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        /// Si son funciones scan y print, tratarlos diferente porque no hacen falta
        /// reserverStack ni freeStack

        if (this.funcDef.isExternal()) {
            for (int i = 0; i < this.args.size(); ++i) {
                var expr = this.args.get(i);
                expr.compileAsExpr(out);
            }

            /// Llamar a la funcion desde WASM
            out.call(this.funcDef.getIden());
            return;
        }

        var params = this.funcDef.getParams();
        int oneIfMethod = 0;
        if(func instanceof StructAccess) {
            // Estamos llamando aun méotodo.
            // Tenemos la dirección del método en la pila
            oneIfMethod = 1;
            out.set_local("temp");
            out.get_global("SP");
            out.i32_const(4 + 4 + params.get(0).getOffset()/*Esta llamada debería dar 0*/);
            out.i32_add();
            out.get_local("temp");
            out.i32_store();
        }

        out.comment("Copying arguments to stack");
        // Vamos eliminando las direcciones de parámetros con su expresion
        // correspondiente
        for (int i = 0; i < this.args.size(); ++i) {
            var param = params.get(i + oneIfMethod);
            var expr = this.args.get(i);

            /// Calcular primero las direcciones de los parámetros
            out.get_global("SP");
            out.i32_const(4 + 4 + param.getOffset());
            out.i32_add();

            if (param.isRef()) {
                out.comment("Copying reference of " + param.getIden() + " to stack");
                var designator = (Designator) expr;
                designator.compileAsDesig(out);
            } else {
                expr.compileAsExpr(out);
            }

            out.i32_store(); // Con la posición calculada en el bucle anterior
        }

        out.comment("PERFORMING FUNCTION CALL");
        out.call(this.funcDef.getIden());

        out.comment("FUNCTION CALL DONE");
    }
}
