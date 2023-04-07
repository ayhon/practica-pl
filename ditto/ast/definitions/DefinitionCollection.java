package ditto.ast.definitions;

import java.util.List;
import java.util.ArrayList;

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