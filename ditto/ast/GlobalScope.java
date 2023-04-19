package ditto.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
