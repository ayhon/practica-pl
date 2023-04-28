package ditto.ast.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ditto.ast.GlobalContext;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;

public class DefStruct extends Definition {
    private final String name;
    private final Map<String, DefVar> attributes;
    private final Map<String, DefFunc> methods;

    public DefStruct(String name) {
        this.name = name;
        this.attributes = new HashMap<>();
        this.methods = new HashMap<>();
    }

    public DefStruct(String name, List<DefVar> attributes, List<DefFunc> methods) {
        this.name = name;
        this.attributes = attributes.stream().collect(Collectors.toMap(DefVar::getIden, Function.identity()));
        this.methods = methods.stream().collect(Collectors.toMap(DefFunc::getIden, Function.identity()));
    }

    public DefVar getAttribute(String iden) {
        return this.attributes.get(iden);
    }

    public DefFunc getMethod(String iden) {
        return this.methods.get(iden);
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

    @Override
    public Type type() {
        Map<String, Type> fieldTypes = new HashMap<>();

        for (DefVar attribute : attributes.values()) {
            fieldTypes.put(attribute.getIden(), attribute.type());
        }

        return new StructType(name, fieldTypes);
    }

    @Override
    public void bind(GlobalContext global, LocalContext local) {
        /// Add the struct to the global scope
        global.addStruct(this);
        // Cuando entramos en el Struct tenemos que resetear el local context
        local.pushLightScope();
        /// Llamar a bind de los hijos
        super.bind(global, local);
    }

    @Override
    public void compile(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
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
}