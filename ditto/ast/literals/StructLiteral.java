package ditto.ast.literals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ditto.ast.GlobalContext;
import ditto.ast.LocalContext;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.definitions.DefStruct;
import ditto.ast.definitions.DefVar;
import ditto.ast.expressions.Expr;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class StructLiteral extends Literal {
    private final String iden;
    private final List<String> module;
    private final Map<String, Expr> fieldValues;
    private DefStruct definition;

    public StructLiteral(List<String> name, Map<String, Expr> fieldValues) {
        this.iden = name.get(name.size() - 1);
        this.module = name.subList(0, name.size() - 1);
        this.fieldValues = fieldValues;
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
    public void bind(GlobalContext global, LocalContext local) {
        /**
         * Para StructLiteral, tengo que asociar:
         * 1. Su definición
         * 2. Los valores de sus campos
         */
        this.definition = global.getStruct(module, iden);
        if (definition == null) {
            throw new TypeError(String.format("No existe struct con identificador %s::%s", module, iden));
        }

        /// Hacer bind de los campos
        super.bind(global, local);
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.addAll(fieldValues.values());
        return children;
    }
}