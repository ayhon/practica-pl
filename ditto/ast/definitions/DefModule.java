package ditto.ast.definitions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.GlobalContext;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.Type;
import ditto.ast.types.VoidType;

public class DefModule extends Node {
    private final String name;

    public DefModule(String name) {
        this.name = name;
    }

    public DefModule(List<String> listName) {
        this.name = String.join("::", listName);
    }

    @Override
    public String getAstString() {
        return "import";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(name);
    }

    @Override
    public Type type() {
        return VoidType.getInstance();
    }

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

    @Override
    public void bind(GlobalContext global, LocalContext local) {
        /// Add the module to the global scope
        global.addGlobalModule(this);
    }

    public String getIden() {
        return name;
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList();
    }
}
