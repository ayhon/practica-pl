package ditto.ast.literals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ditto.ast.Module;
import ditto.ast.Identifier;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefStruct;
import ditto.ast.definitions.DefVar;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;
import ditto.errors.SemanticError;
import ditto.errors.TypeError;

public class StructLiteral extends Literal {
    private final Identifier iden;
    private final Map<String, Expr> fieldValues;
    private DefStruct definition;

    public StructLiteral(Identifier iden, Map<String, Expr> fieldValues) {
        this.iden = iden;
        this.fieldValues = fieldValues;
    }

    public Identifier getIden() {
        return iden;
    }

    @Override
    public String getAstString() {
        return "struct";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(iden, fieldValues);
    }

    @Override
    public Type type() {
        return this.definition.type();
    }

    @Override
    public void typecheck() {
        /// Hay que comprobar que no define campos de más,
        /// y coincide el tipo de cada campo definido con su tipo correspondiente en
        /// DefStruct

        /// Antes de todo hacer el typecheck de sus campos
        super.typecheck();

        for (var entry : fieldValues.entrySet()) {
            DefVar field = this.definition.getAttribute(entry.getKey());
            if (field == null) {
                /// No existe este campo
                throw new TypeError(String.format("No existe campo [%s] en struct [%s]", entry.getKey(), this.iden));
            }

            if (!field.type().equals(entry.getValue().type())) {
                throw new TypeError(String.format(
                        "El tipo del campo [%s] en struct [%s] no coincide con la definicion original de [%s]",
                        entry.getKey(), this.iden, field.type()));
            }
        }
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
    public void bind(Module global, LocalContext local) {
        if(iden.hasModule()){
            definition = global.getModule(iden.getModule()).getStruct(iden.getName());
        } else {
            definition = global.getStruct(iden.getName());
        }

        /// En realidad no hay que hacer bind de los campos. Estos sabemos que se
        /// encontrarán en el
        /// tipo específico del struct, y no en un ámbito local o global.
        // /// Hacer bind de los campos
        // super.bind(global, local);
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.addAll(fieldValues.values());
        return children;
    }
}