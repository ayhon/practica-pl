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
    private Type type = null;
    private final List<Object> ast_args;

    public List<Expr> getElements() {
        return elements;
    }

    public ArrayLiteral(List<Expr> elements) {
        this.elements = elements;
        this.ast_args = Arrays.asList(elements); // ast_args es elements pero como List<Object> en vez de List<Expr>
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

        this.ast_args = new ArrayList<>();
        this.ast_args.add("fill = " + fill.toString());
        this.ast_args.add("numberOfElem = " + numberOfElem.toString());
    }

    @Override
    public String getAstString() {
        return "arr";
    }

    @Override
    public List<Object> getAstArguments() {
        return ast_args;
    }

    @Override
    public Type type() {
        return this.type;
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

        this.type = new ArrayType(elementType);
    }

    @Override
    public Object getValue() {
        return this.elements;
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compileAsExpr'");
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.addAll(elements);
        children.add(numberOfElem);
        return children;
    }
}
