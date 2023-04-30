package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import ditto.ast.Identifier;
import ditto.ast.Context;
import ditto.ast.Delta;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefFunc.Param;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;

public class DefStruct extends Definition {
    private final String name;
    private final Map<String, DefVar> attributes;
    private final Map<String, DefFunc> methods;
    private StructType type;

    public DefStruct(String name, List<Definition> definitions) {
        this.name = name;
        this.attributes = definitions.stream().filter(def -> def instanceof DefVar).map(def -> (DefVar) def)
                .collect(Collectors.toMap(DefVar::getIden, Function.identity()));

        Map<String, Type> fieldTypes = new HashMap<>();
        for (DefVar attribute : this.attributes.values()) {
            fieldTypes.put(attribute.getIden(), attribute.getType());
        }

        this.type = new StructType(new Identifier(name), fieldTypes);

        this.methods = definitions.stream().filter(def -> def instanceof DefFunc)
                .map(def -> this.toMethod((DefFunc) def))
                .collect(Collectors.toMap(DefFunc::getIden, Function.identity()));
    }

    /**
     * Convertir un metodo de un struct en una funcion con un parametro extra this,
     * que es el puntero al struct actual
     */
    private DefFunc toMethod(DefFunc func) {
        List<Param> params = new ArrayList<>();
        params.add(new DefFunc.Param(this.type, func.getIden(), true));
        params.addAll(func.getParams());
        return new DefFunc(func.getIden(), params, func.getResult(), func.getBody());
    }

    public DefVar getAttribute(String iden) {
        return this.attributes.get(iden);
    }

    public StructType getType() {
        return this.type;
    }

    public DefFunc getMethod(String iden) {
        return this.methods.get(iden);
    }

    public Set<Entry<String, DefVar>> getAttributes() {
        return this.attributes.entrySet();
    }

    public Set<Entry<String, DefFunc>> getMethods() {
        return this.methods.entrySet();
    }

    public Definition getAttributeOrMethod(String iden) {
        Definition def = this.attributes.get(iden);
        if (def == null)
            def = this.methods.get(iden);

        return def;
    }

    @Override
    public String getAstString() {
        return "def-struct";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(name, attributes, methods);
    }

    public String getIden() {
        return this.name;
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<>();
        children.add(type);
        children.addAll(attributes.values());
        children.addAll(methods.values());
        return children;
    }

    @Override
    public void bind(Context ctx) {
        ctx.add(this);
        ctx.pushScope();
        super.bind(ctx);
        ctx.popScope();
    }

    @Override
    public Type type() {
        return getType();
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }

    @Override
    public void computeOffset(Delta delta) {
        Delta d = new Delta();

        for (Node val : this.attributes.values()) {
            val.computeOffset(d);
        }
    }
}