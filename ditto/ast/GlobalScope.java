package ditto.ast;

import java.util.List;

import ditto.ast.definitions.DefStruct;
import ditto.ast.definitions.Definition;

public class GlobalScope {
    // Aparte de Map<String, Definition> tiene que incluir un mapeo de modulos y de estructuras
    /**
     * Devuelve la variable global en el módulo actual `iden`.
     * 
     * @param iden
     * @return
     */
    public Definition getGlobalVariable(String iden) {
        return null;
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

    public void addModule(String name) {
        
    }
}
