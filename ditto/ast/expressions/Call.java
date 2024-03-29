package ditto.ast.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ditto.ast.CompilationProgress;
import ditto.ast.Delta;
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
    private int position;

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
        String output = "call";

        if (this.getProgress().atLeast(CompilationProgress.FUNC_SIZE_AND_DELTAS))
            output += String.format(" [delta = %d]", this.position);

        return output;
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

        boolean isStructMethod = this.func instanceof StructAccess;
        if (isStructMethod) {
            this.args.add(0, ((StructAccess) this.func).getStruct());
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

    @Override
    public void computeOffset(Delta lastDelta) {
        super.computeOffset(lastDelta);
        /// Asigno una posición de memoria para el resultado de la función
        position = lastDelta.useNextOffset(this.funcDef.getResult().size());
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

    private boolean generatedExecCode = false;

    @Override
    public void compileAsExpr(ProgramOutput out) {
        /// Generar código para llamar a la función
        /// Esto no hace falta si estamos en una llamada a una función anidada, porque
        /// se habria compilado antes
        if (!generatedExecCode) {
            this.compileAsInstruction(out);
        }

        /// Y luego cargar el resultado en la pila
        out.mem_local(this.position);
        if (this.type().isBasic) {
            out.i32_load();
        } else {
            throw new RuntimeException("No puedes llamar compileAsExpr de tipo no basico");
        }
    }

    @Override
    public void compileAsAssign(ProgramOutput out) {
        if (!generatedExecCode) {
            this.compileAsInstruction(out);
        }

        if (this.type().isBasic) {
            out.mem_local(this.position);
            out.i32_load();
            out.i32_store();
        } else {
            /// Con copyn
            /// Copia no basica
            out.comment("FROM");
            out.mem_local(this.position);

            out.comment("TO");
            out.call(ProgramOutput.SWAP);

            out.comment("SIZE");
            out.i32_const(this.type().size() / 4);

            out.call(ProgramOutput.COPYN);
        }
    }

    /*
     * Se encarga de generar código para llamar a la función, y guardar el resultado
     * en la posición de memoria asignada localmente (this.position)
     */
    @Override
    public void compileAsInstruction(ProgramOutput out) {
        /// Si tuviese funciones anidadas, hay que ejecutar primero esos de uno en uno,
        /// recursivamente
        /// Y luego ejecutar la función en sí, copiando el resultado a la posición de
        /// memoria asignada
        generatedExecCode = true;

        out.comment("Evaluando la llamada: " + this.decompile());

        var nestedCalls = this.args.stream().filter(arg -> arg instanceof Call).map(arg -> (Call) arg)
                .collect(Collectors.toList());

        if (nestedCalls.size() > 0) {
            out.comment("Evaluating nested function calls");
            out.indented(() -> {
                for (var call : nestedCalls) {
                    call.compileAsInstruction(out);
                }
            });
            out.comment("End evaluating nested function calls");
        }

        /// Si son funciones scan y print, tratarlos diferente porque no hacen falta
        /// reserverStack ni freeStack
        if (this.funcDef.isExternal()) {
            for (int i = 0; i < this.args.size(); ++i) {
                var expr = this.args.get(i);
                expr.compileAsExpr(out);
            }

            /// Guardar el resultado en la posición de memoria asignada para caso scan
            if (this.funcDef.getResult().size() > 0) {
                out.mem_local(this.position);
            }

            /// Llamar a la funcion desde WASM
            out.call(this.funcDef.getIden());

            if (this.funcDef.getResult().size() > 0) {
                out.i32_store();
            }

            return;
        }

        /// Para funciones normales
        var params = this.funcDef.getParams();

        out.comment("Copying arguments to stack");
        boolean isStructMethod = this.func instanceof StructAccess;

        for (int i = 0; i < this.args.size(); ++i) {
            var param = params.get(i);
            var expr = this.args.get(i);

            out.comment("Copying argument " + param.getIden() + " to stack");

            /// Calcular primero las direcciones de los parámetros
            out.get_global("SP");
            out.i32_const(4 + 4 + param.getOffset());
            out.i32_add();

            if (param.isRef() || (isStructMethod && i == 0)) {
                /// En el caso de metodo struct, copiar la direccion del struct tal cual
                out.comment("Copying reference of " + param.getIden() + " to stack");
                var designator = (Designator) expr;
                designator.compileAsDesig(out);
                out.i32_store();
            } else {
                expr.compileAsAssign(out);
            }
        }

        out.comment("Poner la direccion de memoria donde guardar el resultado");
        out.get_global("SP");
        out.i32_const(this.funcDef.getSize() + 4 + 4);
        out.i32_add();
        out.mem_local(this.position);
        out.i32_store();

        out.comment("PERFORMING FUNCTION CALL");
        out.call(this.funcDef.getIden());
        out.comment("FUNCTION CALL DONE");
    }
}
