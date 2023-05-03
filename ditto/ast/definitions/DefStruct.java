package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ditto.ast.Context;
import ditto.ast.Delta;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefFunc.Param;
import ditto.ast.types.StructType;

public class DefStruct extends Definition {
    private final Map<String, DefVar> attributes;
    private final Map<String, DefFunc> methods;

    public DefStruct(String name, List<Definition> definitions) {
        super(name);
        this.attributes = definitions.stream().filter(def -> def instanceof DefVar).map(def -> (DefVar) def)
                .collect(Collectors.toMap(DefVar::getIden, Function.identity()));

        this.type = new StructType(this);

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

    public int getOffset(String name) {
        // Devolvemos el offset(delta) del campo
        return this.attributes.get(name).getOffset();
    }

    public StructType getType() {
        return (StructType) this.type;
    }

    public DefFunc getMethod(String iden) {
        return this.methods.get(iden);
    }

    public Map<String, DefVar> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    public Map<String, DefFunc> getMethods() {
        return Collections.unmodifiableMap(this.methods);
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
        return Arrays.asList(this.getIden(), attributes, methods);
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
    public void compileAsInstruction(ProgramOutput out) {
        // Compila sus métodos, pues estos no son más que funciones
        for(DefFunc method : this.methods.values()) {
            method.compileAsInstruction(out);
        }
    }

    @Override
    public void computeOffset(Delta delta) {
        Delta d = new Delta();

        for (Node val : this.attributes.values()) {
            val.computeOffset(d);
        }
    }
}