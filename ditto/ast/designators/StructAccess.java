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
            Type type = ((StructType) struct_type).getFieldOrMethodType(name);
            if (type == null) {
                throw new SemanticError("Struct " + struct_type + " has no field named " + name);
            }
            return type;

        } else {
            throw new SemanticError("Cannot access a field of a non-struct type");
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
    public void generateCode(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

}
