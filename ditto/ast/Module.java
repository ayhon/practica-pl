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
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;
import ditto.errors.SemanticError;

public class Module extends Node {
    private final Map<String, DefModule> modules;
    private final List<Definition> definitions;
    private final Scope globalScope = new Scope();
    private String name;
    private String classFolder;

    public Module(List<DefModule> imports, List<Definition> definitions) {
        this.modules = Collections
                .unmodifiableMap(imports.stream().collect(Collectors.toMap(DefModule::getIden, Function.identity())));
        this.definitions = definitions;
        loadBuiltins();
    }

    private void loadBuiltins() {
        /// Añadir función scan y print
        /// scan recibe un entero y no devuelve nada
        this.globalScope.add(
                new DefFunc(
                        "scan",
                        Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "dest")),
                        new ArrayList<>()));

        /// print recibe un entero y devuelve un entero
        this.globalScope.add(
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
            return mod.getModule().getDefinition(new Identifier(iden.getName()));
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
        return Arrays.asList(modules, definitions);
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
        children.addAll(this.definitions);
        System.err.println(this.definitions);
        return children;
    }

    public void setClassFolder(String classFolder) {
        this.classFolder = classFolder;
    }

    public String getClassFolder() {
        return classFolder;
    }
}