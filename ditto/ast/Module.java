package ditto.ast;
import java.util.List;

import ditto.ast.definitions.*;


public class Module {
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
}