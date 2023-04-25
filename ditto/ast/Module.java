package ditto.ast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.definitions.*;
import ditto.ast.types.Type;


public class Module extends Node {
    private final List<DefModule> imports;
    private final List<DefFunc> functions;
    private final List<DefStruct> structs;
    private final List<DefVar> globals;
    
    public List<DefModule> getImports() {
        return imports;
    }
    
    public List<DefFunc> getFunctions() {
        return functions;
    }
    
    public List<DefStruct> getStructs() {
        return structs;
    }
    
    public List<DefVar> getGlobals() {
        return globals;
    }
    
    public Module(List<DefModule> imports, DefinitionCollection definitions) {
        this.imports = imports;
        this.functions = definitions.getFunctions();
        this.structs = definitions.getStructs();
        this.globals = definitions.getVariables();
    }
    public class DefinitionCollection {
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
    public String getAstString() { return "module"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(imports, functions, structs, globals); }

    public void bind() {
        bind(new GlobalContext(), new LocalContext());
    }
    @Override
    public Type type() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'type'");
    }

    @Override
    public void compile(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

    @Override
    public List<Node> getAstChildren() {

        List<Node> children = new ArrayList<Node>();

        for (DefModule imp : imports) {
            children.add(imp);
        }

        for (DefFunc func : functions) {
            children.add(func);
        }

        for (DefStruct struct : structs) {
            children.add(struct);
        }

        for (DefVar var : globals) {
            children.add(var);
        }

        return children;
    }
}