package ditto.ast.literals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;

public class StructLiteral extends Literal {

    private final String name;
    private final Map<String,Expr> fieldValues;

    public StructLiteral(String name, Map<String,Expr> fieldValues) {
        this.name = name;
        this.fieldValues = fieldValues;
    }
    
    @Override
    public String getAstString() { return "struct"; }

    @Override
    public List<Object> getAstArguments() { 
        ArrayList<Object> res = new ArrayList<Object>();
        res.add(name);
        res.add(fieldValues);
        return res; 
    }

    @Override
    public Type type() { return new StructType(name); } 

    @Override
    public Object getValue() {
        return fieldValues;
    }

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }
}