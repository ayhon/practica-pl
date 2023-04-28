package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.GlobalContext;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefStruct;
import ditto.ast.definitions.Definition;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class StructAccess extends Designator {
    private final Designator struct;
    private final String name;
    private DefStruct defStruct;
    private Definition definition;

    public StructAccess(Designator struct, String name) {
        this.name = name;
        this.struct = struct;
    }

    public Designator getStruct() {
        return struct;
    }

    public String getField() {
        return name;
    }

    @Override
    public void bind(GlobalContext global, LocalContext local) {
        /// Llamar recursivamente al bind del this.struct y hacer bind
        super.bind(global, local);
        this.typecheck();

        /// Si pasa el typecheck, entonces struct es StructType, y struct.getDefinition
        /// devuelve DefStruct
        this.defStruct = (DefStruct) this.struct.getDefinition();

        /// Y nuestra definicion seria un DefVar o un DefFunc
        this.definition = this.defStruct.getAttributeOrMethod(name);
    }

    @Override
    public Type type() {
        /// Nota: typecheck() tiene que haber sido llamado antes de llamar a este metodo
        Type struct_type = struct.type();
        Type type = ((StructType) struct_type).getFieldOrMethodType(name);
        return type;
    }

    @Override
    public void typecheck() {
        Type struct_type = struct.type();
        if (!(struct_type instanceof StructType)) {
            throw new TypeError(String.format("Cannot access a field of a non-struct type '%s'", struct_type));
        }

        Type type = ((StructType) struct_type).getFieldOrMethodType(name);
        if (type == null) {
            throw new TypeError("Struct " + struct_type + " has no field named " + name);
        }
    }

    @Override
    public String getAstString() {
        return "field";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(struct, name);
    }

    @Override
    public void compileAsDesig(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsDesig'");
    }

    @Override
    public List<Node> getAstChildren() {
        return Arrays.asList(struct);
    }

    @Override
    public Definition getDefinition() {
        return this.definition;
    }
}
