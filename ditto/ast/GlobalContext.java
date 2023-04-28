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
                Arrays.asList(new DefFunc.Param(IntegerType.getInstance(), "src")),IntegerType.getInstance(),  // Parameters
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
     * Devuelve la variable global `iden` en el módulo `module`.
     * 
     * @param module
     * @param iden
     * @return
     */
    public DefVar getImported(String module, String iden) {

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
        return globalFunc.get(iden);
    }

    public DefModule getModule(String iden) {
        return globalModule.get(iden);
    }

    public DefStruct getStruct(String iden) {
        return globalStruct.get(iden);
    }
}
