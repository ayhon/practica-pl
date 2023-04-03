package ditto.ast.definitions;

import java.util.List;

import ditto.ast.Node;

public class DefFunc extends Node {

    @Override
    public String getAstString() {
        return "def-func";
    }

    @Override
    public List<Object> getAstArguments() {
        throw new UnsupportedOperationException("Unimplemented method 'getAstArguments' for DefFunc");
    }

}
