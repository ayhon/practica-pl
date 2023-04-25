package ditto.ast.literals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ditto.ast.GlobalContext;
import ditto.ast.LocalContext;
import ditto.ast.Node;
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

    private Type type = null;

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
        Map<String, Type> fieldTypes = new TreeMap<String, Type>();
        for (Map.Entry<String, Expr> entry : fieldValues.entrySet()) {
            Type aux = entry.getValue().type();
            if(aux.equals(null))
                throw new RuntimeException("Type of field '" + entry.getKey() + "' is null");
            fieldTypes.put(entry.getKey(), aux);
        }
        this.type = new StructType(iden, fieldTypes);
        return this.type;
    }

    @Override
    public Object getValue() {
        return fieldValues;
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsExpr'");
    }

    @Override
    public void bind(GlobalContext global, LocalContext local) {
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

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.addAll(fieldValues.values());
        return children;
    }
}