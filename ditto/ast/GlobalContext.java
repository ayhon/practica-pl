package ditto.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ditto.ast.definitions.DefFunc;
import ditto.ast.definitions.DefModule;
import ditto.ast.definitions.DefStruct;
import ditto.ast.definitions.DefVar;
import ditto.ast.definitions.Definition;

public class GlobalContext {
    // Aparte de Map<String, Definition> tiene que incluir un mapeo de modulos y de estructuras
    private Map<String, Definition> globalVar;
    private Map<String, Definition> globalFunc;
    private Map<String, DefStruct> globalStruct;
    private Map<String, DefModule> globalModule;

    /**
     * Crea un nuevo contexto global.
     */
    public GlobalContext() {
        globalVar = new HashMap<>();
        globalFunc = new HashMap<>();
        globalStruct = new HashMap<>();
        globalModule = new HashMap<>();
    }

    /**
     * Devuelve la variable global en el módulo actual `iden`.
     * 
     * @param iden
     * @return
     */
    public Definition getGlobalVariable(String iden) {
        return globalVar.get(iden);
    }

    /**
     * Devuelve la variable global `iden` en el módulo `module`.
     * 
     * @param module
     * @param iden
     * @return
     */
    public Definition getImportedGlobal(List<String> module, String iden) {
    
        return null;
    }

    /**
     * Devuelve la estructura `name` en el módulo `module`.
     * @param module Lista vacía si es el módulo actual.
     * @param name
     * @return null si no existe.
     */
    public DefStruct getStruct(List<String> module, String name) {
        return null;
    }

    public void addGlobalModule(DefModule module) {
        globalModule.put(module.getIden(), module);
    }

    public void addStruct(DefStruct struct) {
        globalStruct.put(struct.getIden(), struct);
    }
    
    public void addGlobalVariable(DefVar var) {
        globalVar.put(var.getIden(), var);
    }

    public void addGlobalFunction(DefFunc func) {
        globalFunc.put(func.getIden(), func);
    }

    public DefFunc getGlobalFunction(String iden) {
        return (DefFunc) globalFunc.get(iden);
    }
    
    public DefVar getGlobalVar(String iden) {
        return (DefVar) globalVar.get(iden);
    }

    public DefModule getGlobalModule(String iden) {
        return globalModule.get(iden);
    }

    public DefStruct getGlobalStruct(String iden) {
        return globalStruct.get(iden);
    }
}
