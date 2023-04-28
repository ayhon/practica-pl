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
import ditto.ast.designators.Var;
import ditto.ast.types.IntegerType;

public class GlobalContext {
    // Aparte de Map<String, Definition> tiene que incluir un mapeo de modulos y de
    // estructuras
    private Map<String, DefVar> globalVar;
    private Map<String, DefFunc> globalFunc;
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

        /// Añadir función scan y print
        /// scan recibe un entero y no devuelve nada
        var scanFunc = new DefFunc("scan", Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "dest")),
                new ArrayList<>());
        globalFunc.put("scan", scanFunc);

        /// print recibe un entero y devuelve un entero
        var printFunc = new DefFunc("print", Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "src")),
                IntegerType.getInstance(), new ArrayList<>());
        globalFunc.put("print", printFunc);
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
    public Definition getImportedGlobal(String module, String iden) {

        return null;
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

    public void addGlobalModule(DefModule module) {
        globalModule.put(module.getIden(), module);
    }

    public void addStruct(DefStruct struct) {
        globalStruct.put(struct.getIden(), struct);
    }

    public void addGlobalVariable(DefVar var) { // TODO: cambiar para que devuelva int
        globalVar.put(var.getIden(), var);
    }

    public void addGlobalFunction(DefFunc func) {
        globalFunc.put(func.getIden(), func);
    }

    public DefFunc getFunction(String iden) {
        return globalFunc.get(iden);
    }

    public DefVar getGlobalVar(String iden) {
        return globalVar.get(iden);
    }

    public DefModule getGlobalModule(String iden) {
        return globalModule.get(iden);
    }

    public DefStruct getGlobalStruct(String iden) {
        return globalStruct.get(iden);
    }
}
