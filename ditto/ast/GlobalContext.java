package ditto.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ditto.ast.definitions.DefFunc;
import ditto.ast.definitions.DefModule;
import ditto.ast.definitions.DefStruct;
import ditto.ast.definitions.DefVar;
import ditto.ast.definitions.Definition;
import ditto.ast.types.IntegerType;
import ditto.errors.SemanticError;

public class GlobalContext {
    // Aparte de Map<String, Definition> tiene que incluir un mapeo de modulos y de
    // estructuras
    private final Module mainModule;
    private Map<String, DefVar> globalVar;
    private Map<String, DefFunc> globalFunc;
    private Map<String, DefStruct> globalStruct;
    private Map<String, DefModule> globalModule;

    /**
     * Crea un nuevo contexto global.
     */
    public GlobalContext(Module mainModule) {
        globalVar = new HashMap<>();
        globalFunc = new HashMap<>();
        globalStruct = new HashMap<>();
        globalModule = new HashMap<>();
        this.mainModule = mainModule;
        addBuiltins();
    }

    private void addBuiltins() {
        /// Añadir función scan y print
        /// scan recibe un entero y no devuelve nada
        globalFunc.put("scan",
                new DefFunc(
                        "scan", // Name
                        Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "dest")), // Parameters
                        new ArrayList<>() // Body
                ));

        /// print recibe un entero y devuelve un entero
        globalFunc.put("print",
                new DefFunc(
                        "print", // Name
                        Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "src")), IntegerType.getInstance(), // Parameters
                        new ArrayList<>() // Body
                ));
    }

    /**
     * Devuelve la variable global en el módulo actual `iden`.
     * 
     * @param iden
     * @return
     */
    public DefVar getVar(String iden) {
        return globalVar.get(iden);
    }

    /**
     * Devuelve la definición global correspondiente a `iden` del módulo `module`.
     * 
     * @param module
     * @param iden
     * @return
     */
    public Definition getImportedDef(String moduleName, String iden) {
        Module module = getModule(moduleName);
        Definition def = module.getDefinition(iden);
        if (def == null)
            throw new SemanticError("Coldn't find " + iden + " in module " + moduleName);
        return def;
    }

    public Module getModule(String moduleName) {
        DefModule module_def = globalModule.get(moduleName);
        if (module_def == null)
            throw new SemanticError("Module " + moduleName + " not found");
        return module_def.getModule();
    }

    /**
     * Devuelve la estructura `name` en el módulo `module`.
     * 
     * @param module Lista vacía si es el módulo actual.
     * @param name
     * @return null si no existe.
     */
    public DefStruct getStruct(List<String> module, String name) {
        return null;
    }

    public void addModule(DefModule module) {
        globalModule.put(module.getIden(), module);
    }

    public void addStruct(DefStruct struct) {
        globalStruct.put(struct.getIden(), struct);
    }

    public void addVariable(DefVar var) { // TODO: cambiar para que devuelva int
        globalVar.put(var.getIden(), var);
    }

    public void addFunction(DefFunc func) {
        if (globalFunc.containsKey(func.getIden()))
            throw new RuntimeException("Function " + func.getIden() + " already defined");
        globalFunc.put(func.getIden(), func);
    }

    public DefFunc getFunction(String iden) {
        var def = globalFunc.get(iden);
        if (def == null)
            throw new SemanticError("Couldn't find function " + iden);
        return def;
    }

    public DefModule getDefModule(String iden) {
        var def = globalModule.get(iden);
        if (def == null)
            throw new SemanticError("Couldn't find module " + iden);
        return def;
    }

    public DefStruct getStruct(String iden) {
        var def = globalStruct.get(iden);
        if (def == null)
            throw new SemanticError("Couldn't find struct " + iden);
        return def;
    }

    public Definition getDefinition(String iden) {
        var def = mainModule.getDefinition(iden);
        if (def == null)
            throw new SemanticError("Couldn't find definition " + iden);
        return def;
    }
}
