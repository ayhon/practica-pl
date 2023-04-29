package ditto.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ditto.ast.definitions.*;
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;
import ditto.errors.SemanticError;

public class Module extends Node {
    private final Map<String, DefModule> modules;
    private final Map<String, DefFunc> functions;
    private final Map<String, DefStruct> structs;
    private final Map<String, DefVar> variables;
    private String classFolder;

    public Module(List<DefModule> imports, DefinitionCollection definitions) {
        this.modules = imports.stream().collect(Collectors.toMap(DefModule::getIden, Function.identity()));
        this.functions = definitions.getFunctions().stream()
                .collect(Collectors.toMap(DefFunc::getIden, Function.identity()));
        this.structs = definitions.getStructs().stream()
                .collect(Collectors.toMap(DefStruct::getIden, Function.identity()));
        this.variables = definitions.getVariables().stream()
                .collect(Collectors.toMap(DefVar::getIden, Function.identity()));
    }

    public void addFunc(DefFunc func) {
        if (functions.containsKey(func.getIden()))
            throw new SemanticError("Function " + func.getIden() + " already defined");
        functions.put(func.getIden(), func);
    }

    public DefFunc getFunc(String iden) {
        var def = this.functions.get(iden);
        if (def == null)
            throw new SemanticError("Couln't find function " + iden);
        return def;
    }

    public void addStruct(DefStruct struct) {
        structs.put(struct.getIden(), struct);
    }

    public DefStruct getStruct(String iden) {
        var def = this.structs.get(iden);
        if (def == null)
            throw new SemanticError("Couln't find struct " + iden);
        return def;
    }

    public void addVar(DefVar var) {
        variables.put(var.getIden(), var);
    }

    public DefVar getVar(String iden) {
        var def = this.variables.get(iden);
        if (def == null)
            throw new SemanticError("Couln't find global " + iden);
        return def;
    }

    public Definition getDefinition(String iden) {
        Definition def = variables.get(iden);
        if (def == null)
            def = functions.get(iden);
        if (def == null)
            throw new SemanticError("No global definition found for " + iden);
        return def;
    }

    public void addModule(DefModule module) {
        modules.put(module.getIden(), module);
    }

    public Module getModule(String moduleName) {
        DefModule module_def = modules.get(moduleName);
        if (module_def == null)
            throw new SemanticError("Module " + moduleName + " not found");
        return module_def.getModule();
    }

    public static class DefinitionCollection {
        private List<DefVar> variables;

        public List<DefVar> getVariables() {
            return variables;
        }

        private List<DefFunc> functions;

        public List<DefFunc> getFunctions() {
            return functions;
        }

        private List<DefStruct> structs;

        public List<DefStruct> getStructs() {
            return structs;
        }

        public void setStructs(List<DefStruct> structs) {
            this.structs = structs;
        }

        public DefinitionCollection() {
            this.variables = new ArrayList<>();
            this.functions = new ArrayList<>();
            this.structs = new ArrayList<>();
        }

        public void add(DefVar var) {
            this.variables.add(var);
        }

        public void add(DefFunc func) {
            this.functions.add(func);
        }

        public void add(DefStruct struct) {
            this.structs.add(struct);
        }
    }

    @Override
    public String getAstString() {
        return "module";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(modules, functions, structs, variables);
    }

    public void bind() {
        System.out.println("[DEBUG]: Start binding module");
        bind(this, null);
        System.out.println("[DEBUG]: Finished binding module");
    }

    @Override
    public Type type() {
        return VoidType.getInstance();
    }

    @Override
    public void typecheck() {
        this.bind();
        System.out.println("[DEBUG]: Start typechecking module");
        super.typecheck();
        System.out.println("[DEBUG]: Finished typechecking module");
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
        children.addAll(this.functions.values());
        children.addAll(this.structs.values());
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