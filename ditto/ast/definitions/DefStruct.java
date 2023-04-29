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

import ditto.ast.Module;
import ditto.ast.Identifier;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;

public class DefStruct extends Definition {
    private final String name;
    private final Map<String, DefVar> attributes;
    private final Map<String, DefFunc> methods;
    private StructType type;

    public DefStruct(String name) {
        this.name = name;
        this.attributes = new HashMap<>();
        this.methods = new HashMap<>();
    }

    public DefStruct(String name, List<DefVar> attributes, List<DefFunc> methods) {
        this.name = name;
        this.attributes = attributes.stream().collect(Collectors.toMap(DefVar::getIden, Function.identity()));
        this.methods = methods.stream().collect(Collectors.toMap(DefFunc::getIden, Function.identity()));
        Map<String, Type> fieldTypes = new HashMap<>();

        for (DefVar attribute : this.attributes.values()) {
            fieldTypes.put(attribute.getIden(), attribute.getType());
        }

        type = new StructType(new Identifier(name), fieldTypes);
    }

    public DefVar getAttribute(String iden) {
        return this.attributes.get(iden);
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

    public Type getType() {
        return this.type;
    }

    @Override
    public Type type() {
        return getType();
    }

    @Override
    public void bind(Module global, LocalContext local) {
        /// Add the struct to the global scope
        global.addStruct(this);
        /// TODO: Quitar esto. No hay declaraciones de variables locales dentro de la
        /// definición de un Struct.
        /// Sus únicos hijos son declaraciones de variables y funciones que no
        /// pertenecen a ningún ambito.
        /// No se tiene que continuar la vinculación ni añadir un nuevo contexto ligero.
        // /// Cuando entramos en el Struct tenemos que resetear el local context
        // local.pushLightScope();
        // /// Llamar a bind de los hijos
        // super.bind(global, local);
    }

    public String getIden() {
        return this.name;
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<>();
        children.addAll(attributes.values());
        children.addAll(methods.values());
        return children;
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsInstruction'");
    }

    @Override
    public int computeDelta(int lastPosition) {
        int delta = 0;
        for (Node child : getAstChildren()) {
            delta = child.computeDelta(delta);
        }

        return lastPosition + delta;
    }
}