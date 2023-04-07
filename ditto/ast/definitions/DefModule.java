package ditto.ast.definitions;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;

public class DefModule extends Node {
    private final String name;
    
    public DefModule(String name) {
        this.name = name;
    }
    public DefModule(List<String> listName) {
        this.name = String.join("::", listName);
    }
    
    @Override
    public String getAstString() { return "import";}
    @Override
    public List<Object> getAstArguments() { return Arrays.asList(name); }
}
