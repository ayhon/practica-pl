package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.types.Type;

public class Var extends Node implements Designator {
    public String name;

    @Override
    public String getAstString(){return "var";}

    @Override
    public List<Object> getAstArguments(){return Arrays.asList(name);}

    public Var(List<String> name){
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        for(String s : name){
            sb.append(s);
            if(idx != name.size() - 1)
                sb.append("::");
            idx += 1;
        }
        this.name = sb.toString();
    }
    public Var(String iden) {
        this.name = iden;
    }
    @Override
    public Type getType() { return null; } // This should perform some kind of typecheck    
}
