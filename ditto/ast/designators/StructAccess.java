package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.Definition;
import ditto.ast.types.ArrayType;
import ditto.ast.types.IntegerType;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class StructAccess extends Designator {
    private final Designator struct;
    private final String name;

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

    public Definition getDefinition(String name) {
        StructType st = (StructType) struct.type();
        return st.getFieldDefinition(name);
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
    public List<Node> getAstChildren() {
        return Arrays.asList(struct);
    }

    @Override
    public void typecheck() {
        super.typecheck();
        Type struct_type = struct.type();

        if (struct_type instanceof ArrayType && name.equals("size")) {
            /// Es de tipo ArrayType y se quiere acceder a su campo size
            this.type = IntegerType.getInstance();
            return;
        }

        if (!(struct_type instanceof StructType)) {
            throw new TypeError(
                    String.format("Cannot access a field of a non-struct type '%s', %s", struct_type, struct));
        }

        Type type = ((StructType) struct_type).getFieldOrMethodType(name);
        if (type == null) {
            throw new TypeError("Struct " + struct_type + " has no field named " + name);
        }

        this.type = type;
    }

    @Override
    public String decompile() {
        return String.format("%s.%s", struct.decompile(), name);
    }

    @Override
    public void compileAsDesig(ProgramOutput out) {
        // 1) Calculamos la direccion del designador
        // 2) Cargamos el offset del campo
        // 3) Sumamos ambos valores
        struct.compileAsDesig(out);
        out.i32_const(((StructType) struct.type()).getOffset(name)); // i32.const delta(*id)
        out.i32_add();                                               // i32.add
    }
}
