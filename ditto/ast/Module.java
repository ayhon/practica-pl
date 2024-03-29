package ditto.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ditto.ast.definitions.*;
import ditto.ast.types.IntegerType;
import ditto.ast.types.VoidType;
import ditto.errors.BindingError;

public class Module extends Node {
    private final Map<String, DefModule> modules;
    private List<Definition> definitions;
    private final Scope globalScope = new Scope();
    private String name;
    private String classFolder;
    private int globalVarSize;
    private final List<Node> astChildren = new ArrayList<>();

    public Module(List<DefModule> imports, List<Definition> definitions) {
        this.modules = Collections
                .unmodifiableMap(imports.stream().collect(Collectors.toMap(DefModule::getIden, Function.identity())));
        this.definitions = definitions;
        astChildren.addAll(this.modules.values());
        astChildren.addAll(this.definitions);
        loadBuiltins();
        this.type = VoidType.getInstance();
    }

    private void loadBuiltins() {
        /// Añadir función scan y print
        /// scan recibe un entero y no devuelve nada
        this.globalScope.add(
                new DefFunc(
                        "scan",
                        Arrays.asList(),
                        IntegerType.getInstance()));

        /// print recibe un entero y devuelve un entero
        this.globalScope.add(
                new DefFunc(
                        "print",
                        Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "src", false)),
                        VoidType.getInstance()));
    }

    public Definition getDefinition(Identifier iden) {
        if (iden.hasModule()) {
            DefModule mod = modules.get(iden.getModule());
            if (mod == null) {
                throw new BindingError(String.format("Module '%s' not found", iden.getModule()));
            }
            return mod.getModule().getDefinition(new Identifier(iden.getName()));
        } else
            return this.globalScope.get(iden.getName());
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public List<Node> getAstChildren() {
        return Collections.unmodifiableList(astChildren);
    }

    public void setClassFolder(String classFolder) {
        this.classFolder = classFolder;
    }

    public String getClassFolder() {
        return classFolder;
    }

    @Override
    public String getAstString() {
        var astString = "module";
        if (this.getProgress().atLeast(CompilationProgress.FUNC_SIZE_AND_DELTAS)) {
            astString += String.format(" [GLOBAL VAR SIZE = %d]", this.globalVarSize);
        }
        return astString;
    }

    public int getGlobalVarSize() {
        return globalVarSize;
    }

    @Override
    public List<Object> getAstArguments() {
        List<Object> args = new ArrayList<>();
        args.addAll(astChildren);
        return args;
    }

    public void bind() {
        Context ctx = new Context(this, this.globalScope);
        bind(ctx);
    }

    @Override
    public void computeTypeSize() {
        this.bind();
        super.computeTypeSize();
    }

    @Override
    public void typecheck() {
        this.computeTypeSize();
        super.typecheck();
    }

    public void computeOffset() {
        this.typecheck();

        /// Para calcular el offset, necesito mover las funciones de modulos a mi modulo
        /// actual, sino no podre calcular
        /// el offset de las variables globales
        moveGlobal();
        computeOffset(new Delta());
    }

    public void moveGlobal() {
        /// Mover las funciones de los modulos a mi modulo actual
        /// Esta llamada seria recursiva, porque los modulos tambien tienen que mover
        /// las funciones de sus modulos
        List<Definition> newDefinitions = new ArrayList<>();
        for (DefModule mod : modules.values()) {
            mod.getModule().moveGlobal();
            for (Definition def : mod.getModule().getDefinitions()) {
                newDefinitions.add(def);
                this.globalScope.add(def);
            }
        }

        for (Definition def : this.definitions) {
            def.addModuleToIden(this.name);
            newDefinitions.add(def);
        }

        this.definitions = newDefinitions;
        this.astChildren.clear();
        this.astChildren.addAll(this.definitions);
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    @Override
    public void computeOffset(Delta delta) {
        super.computeOffset(delta);
        globalVarSize = delta.getOffsetSize();
    }

    @Override
    public void compile(ProgramOutput out) {
        /**
         * Para generar el codigo, tiene que calcular primero el offset (a su vez
         * llamara a -> computeTypeSize() -> typecheck() -> bind())
         */
        this.computeOffset();

        String mainFunction = String.format("%s__main", this.name);
        Boolean hasMainFunction = false;
        for (Definition def : definitions) {
            if (def instanceof DefFunc) {
                DefFunc func = (DefFunc) def;
                if (func.getIden().equals(mainFunction)) {
                    if (hasMainFunction)
                        throw new RuntimeException("Multiple main functions found");
                    hasMainFunction = true;
                }
            }
        }

        if (!hasMainFunction)
            throw new RuntimeException("No main function found");

        out.inStart(mainFunction, () -> {
            /// Las dos primeras posiciones de memoria son para MP antiguo y SP actual
            out.i32_const(0);
            out.i32_const(0);
            out.i32_store();

            /// SP pasara a ser 8 + globalVarSize
            out.i32_const(8 + globalVarSize);
            out.i32_const(4);
            out.i32_store();

            /// Ahora confiugrar el MP
            out.i32_const(0);
            out.set_global("MP");

            /// Ahora configurar el SP
            out.i32_const(8 + globalVarSize);
            out.set_global("SP");

            for (Definition def : this.definitions) {
                if (def instanceof DefVar)
                    def.compile(out);
            }
        });

        /// Y ahora llamar a compile de cada uno
        for (Definition def : this.definitions) {
            if (def instanceof DefFunc || def instanceof DefStruct)
                def.compile(out);
        }
    }
}