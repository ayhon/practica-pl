package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.types.ArrayType;
import ditto.ast.types.IntegerType;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;
import ditto.errors.SemanticError;
import ditto.errors.TypeError;

public class StructAccess extends Designator {
    private final Designator struct;
    private final String name;
    private Type type;

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
    public Type type() {
        /// Nota: typecheck() tiene que haber sido llamado antes de llamar a este metodo
        if (type == null) {
            throw new SemanticError("Can't get type of struct field before typechecking.");
        }
        return type;
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
}
