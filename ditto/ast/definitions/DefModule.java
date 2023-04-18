package ditto.ast.definitions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.Type;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'type'");
    }

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }
}
