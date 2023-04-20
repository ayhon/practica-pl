package ditto.ast.literals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ditto.ast.GlobalScope;
import ditto.ast.LocalContext;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefStruct;
import ditto.ast.expressions.Expr;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;

public class StructLiteral extends Literal {
    private final String iden;
    private final List<String> module;
    private final Map<String, Expr> fieldValues;
    private DefStruct definition = null;

    public StructLiteral(List<String> name, Map<String, Expr> fieldValues) {
        this.iden = name.get(name.size() - 1);
        this.module = name.subList(0, name.size() - 1);
        this.fieldValues = fieldValues;
    }

    @Override
    public String getAstString() {
        return "struct";
    }

    @Override
    public List<Object> getAstArguments() {
        ArrayList<Object> res = new ArrayList<Object>();
        res.add(iden);
        res.add(fieldValues);
        return res;
    }

    @Override
    public Type type() {
        return new StructType(iden, module);
    }

    @Override
    public Object getValue() {
        return fieldValues;
    }

    @Override
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

    @Override
    public void bind(GlobalScope global, LocalContext local) {
        /**
         * Para StructLiteral, tengo que asociar:
         * 1. Su definición
         * 2. Los valores de sus campos
         */
        this.definition = global.getStruct(module, iden);

        for (Expr e : fieldValues.values()) {
            e.bind(global, local);
        }
    }
}