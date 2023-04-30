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
import ditto.errors.BindingError;

public class Module extends Node {
    private final Map<String, DefModule> modules;
    private final List<Definition> definitions;
    private final Scope globalScope = new Scope();
    private String name;
    private String classFolder;
    private int globalVarSize;

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
                        Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "dest", true)),
                        new ArrayList<>()));

        /// print recibe un entero y devuelve un entero
        this.globalScope.add(
                new DefFunc(
                        "print",
                        Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "src", false)),
                        IntegerType.getInstance(),
                        new ArrayList<>()));
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

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.addAll(this.modules.values());
        children.addAll(this.definitions);
        return children;
    }

    public void setClassFolder(String classFolder) {
        this.classFolder = classFolder;
    }

    public String getClassFolder() {
        return classFolder;
    }

    @Override
    public String getAstString() {
        return "module";
    }

    public int getGlobalVarSize() {
        return globalVarSize;
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(modules, definitions);
    }

    public void bind() {
        Context ctx = new Context(this, this.globalScope);
        bind(ctx);
    }

    @Override
    public Type type() {
        return VoidType.getInstance();
    }

    @Override
    public void typecheck() {
        this.bind();
        super.typecheck();
    }

    @Override
    public void computeTypeSize() {
        this.typecheck();
        super.computeTypeSize();
    }

    @Override
    public void computeOffset(Delta lastDelta) {
        Delta delta = new Delta();
        for (Definition def : this.definitions) {
            def.computeOffset(delta);
        }
        globalVarSize = delta.getOffsetSize();
    }

    public void computeOffset() {
        this.computeTypeSize();
        computeOffset(new Delta());
    }

    @Override
    public void compile(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

}