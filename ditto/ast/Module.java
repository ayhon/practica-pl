package ditto.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ditto.ast.definitions.*;
import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;
import ditto.errors.SemanticError;

public class Module extends Node {
    private final Map<String, DefModule> modules;
    private final Map<String, DefFunc> functions = new HashMap<>();
    private final Map<String, DefStruct> structs = new HashMap<>();
    private final Map<String, DefVar> variables = new HashMap<>();
    private final List<Definition> children;
    private final Scope globalScope = new Scope();
    private String name;
    private String classFolder;

    public Module(List<DefModule> imports, List<Definition> definitions) {
        this.modules = imports.stream().collect(Collectors.toMap(DefModule::getIden, Function.identity()));
        children = definitions;
        loadBuiltins();
    }

    private void loadBuiltins() {
        /// Añadir función scan y print
        /// scan recibe un entero y no devuelve nada
        this.functions.put("scan",
                new DefFunc(
                        "scan",
                        Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "dest")),
                        new ArrayList<>()));

        /// print recibe un entero y devuelve un entero
        this.functions.put("print",
                new DefFunc(
                        "print",
                        Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "src")), IntegerType.getInstance(),
                        new ArrayList<>()));
    }

    public Definition getDefinition(Identifier iden) {
        if (iden.hasModule()) {
            DefModule mod = modules.get(iden.getModule());
            if (mod == null) {
                throw new SemanticError(String.format("Module '%s' not found", iden.getModule()));
            }
            return mod.getModule().getDefinition(iden);
        } else
            return this.globalScope.get(iden.getName());
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAstString() {
        return "module";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(modules, children);
    }

    public void bind() {
        Context ctx = new Context(this, this.globalScope);
        System.out.println("[DEBUG]: Start binding module " + this.name);
        bind(ctx);
        System.out.println("[DEBUG]: Finished binding module " + this.name);
    }

    @Override
    public Type type() {
        return VoidType.getInstance();
    }

    @Override
    public void typecheck() {
        this.bind();
        System.out.println("[DEBUG]: Start typechecking module " + this.name);
        super.typecheck();
        System.out.println("[DEBUG]: Finished typechecking module " + this.name);
    }

    @Override
    public void compile(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.addAll(this.modules.values());
        children.addAll(this.structs.values());
        children.addAll(this.functions.values());
        children.addAll(this.variables.values());
        return children;
    }

    public void setClassFolder(String classFolder) {
        this.classFolder = classFolder;
    }

    public String getClassFolder() {
        return classFolder;
    }

    public String dumpGlobals() {
        StringBuilder sb = new StringBuilder();
        sb.append("Modules:\n");
        for (var entry : modules.entrySet()) {
            sb.append(String.format("  %s :\n", entry.getKey()));
            sb.append(entry.getValue().getModule().dumpGlobals().indent(4));
        }
        sb.append("Global variables:\n");
        for (var entry : variables.entrySet()) {
            sb.append(String.format("  %s → %s\n", entry.getKey(), entry.getValue()));
        }
        sb.append("Structs:\n");
        for (var entry : structs.entrySet()) {
            sb.append(String.format("  %s → %s\n", entry.getKey(), entry.getValue()));
        }
        sb.append("Functions:\n");
        for (var entry : functions.entrySet()) {
            sb.append(String.format("  %s → %s\n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}