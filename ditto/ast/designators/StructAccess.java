package ditto.ast.designators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ditto.ast.ProgramOutput;
import ditto.ast.types.StructType;
import ditto.ast.types.Type;
import ditto.errors.SemanticError;

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

    @Override
    public Type type() {
        Type struct_type = struct.type();
        if (struct_type instanceof StructType) {
            Map<String, Type> field_types = ((StructType) struct_type).getFieldTypes();
            if ((field_types.containsKey(name))) {
                return field_types.get(name);
            } else
                throw new SemanticError(
                        "Struct " + ((StructType) struct_type).getName() + " does not have a field named " + name);
        } else
            throw new SemanticError("Cannot access a field of a non-struct type");
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
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

}
