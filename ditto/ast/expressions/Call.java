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
    public void compileAsExpr(ProgramOutput out) {
        /// Reservar la memoria para los argumentos + variables locales + MP + SP
        int stackSize = this.funcDef.getSize() + (2 * 4);
        out.i32_const(stackSize);
        out.reserveStack();

        /// Copiar los argumentos a LOCALS_START ...
        for (int i = 0; i < this.args.size(); ++i) {
            var expr = this.args.get(i);
            var param = this.funcDef.getParams().get(i);
            /// Guardar este resultado en LOCALS_START + 4 * i
            /// Calcular primero la direccion
            out.mem_local(param.getOffset());
            /// Calcular el valor
            expr.compileAsExpr(out);
            /// Guardar el resultado en la direccion
            out.i32_store();
        }

        /// Llamar a la funcion desde WASM
        out.call(this.funcDef.getIden());

        /// Liberar la memoria reservada
        out.freeStack();
    }
}
