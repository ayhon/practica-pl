package ditto.ast;
import java.util.Arrays;
import java.util.List;

import ditto.ast.definitions.*;


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

    @Override
    public String getAstString() { return "module"; }

    @Override
    public List<Object> getAstArguments() { return Arrays.asList(imports, functions, structs, globals); }
}