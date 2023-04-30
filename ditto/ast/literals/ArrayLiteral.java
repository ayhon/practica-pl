package ditto.ast.literals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.ArrayType;
import ditto.ast.types.Type;

public class ArrayLiteral extends Literal {
    private final List<Expr> elements;
    private final Expr numberOfElem;

    public List<Expr> getElements() {
        return elements;
    }

    public ArrayLiteral(List<Expr> elements) {
        this.elements = elements;
        this.numberOfElem = new Natural(elements.size());
    }

    public ArrayLiteral() {
        this(new ArrayList<Expr>());
    }

    public ArrayLiteral(Expr fill, Expr numberOfElem) {
        /// Cuando tenemos un array de tamaño variable y un valor por defecto para cada
        /// elemento
        /// Anotamos el valor por defecto y el tamaño del array
        /// Pero no vamos a crear un ArrayList aqui en Java, porque el tamaño del array
        /// no lo sabemos, y se conoce en momento de ejecución
        this.numberOfElem = numberOfElem;

        this.elements = new ArrayList<>();
        this.elements.add(fill);
    }

    @Override
    public String getAstString() {
        return "arr";
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<>(elements);
        children.add(numberOfElem);
        return children;
    }

    @Override
    public Object getValue() {
        return this.elements;
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(elements, numberOfElem);
    }

    @Override
    public void typecheck() {
        super.typecheck();

        Type elementType = null;
        for (Expr elem : elements) {
            if (elementType == null) {
                elementType = elem.type();
            } else {
                if (!elementType.equals(elem.type())) {
                    throw new RuntimeException("ArrayLiteral: All elements must be of the same type" + elementType
                            + ", but type " + elem.type() + " was found");
                }
            }
        }

        if (elementType == null)
            throw new RuntimeException("ArrayLiteral: Cant determine type of array elements");

        Integer length = numberOfElem.evalIntAtCompileTime();
        if (length == null)
            throw new RuntimeException("ArrayLiteral: Number of elements cannot be determined at compile time");

        this.type = new ArrayType(elementType, length);
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsExpr'");
    }

}
