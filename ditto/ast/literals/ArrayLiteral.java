package ditto.ast.literals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ditto.ast.CompilationProgress;
import ditto.ast.Delta;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.ArrayType;
import ditto.ast.types.Type;

public class ArrayLiteral extends Literal {
    private final List<Expr> elements;
    private final Expr numberOfElem;
    private int position;

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
        String output = "arr";

        if (this.getProgress().atLeast(CompilationProgress.FUNC_SIZE_AND_DELTAS))
            output += String.format(" [delta = %d]", this.position);

        return output;
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
    public void computeOffset(Delta lastDelta) {
        /// Necesito reservar espacio de 1 int para guardar la dirección de inicio del
        /// Array en heap
        /// Porque rellenamos el array primero en el heap, y luego se copia a donde
        /// tiene que copiar
        this.position = lastDelta.useNextOffset(4);

        super.computeOffset(lastDelta);
    }

    @Override
    public String decompile() {
        return "[" + String.join(",", elements.stream().map(Expr::decompile).toList()) + "]";
    }

    @Override
    public void compileAsExpr(ProgramOutput out) {
        out.comment("Evaluando ArrayLiteral: " + this.decompile());

        /// Reservar espacio en heap
        out.i32_const(this.type().size());
        out.call(ProgramOutput.RESERVE_HEAP);

        /// Guardar dirección del inicio del array
        out.mem_local(this.position);
        out.get_global("NP");
        out.i32_store();

        out.indented(() -> {
            /// Evaluar cada elemento (puede contener también tipos no básicos)
            for (int i = 0; i < elements.size(); ++i) {
                var element = elements.get(i);
                out.comment("Guardando elemento " + i + " del array: " + element.decompile());

                if (element.type().isBasic) {
                    /// Caso base, copiar con i32_store
                    out.comment("Es un tipo básico, copiar con i32_store");
                    out.mem_local(this.position);
                    out.i32_load();
                    out.i32_const(i * elements.get(i).type().size());
                    out.i32_add();

                    element.compileAsExpr(out);

                    out.i32_store();
                } else {
                    /// Caso recursivo, copiar con ncopy
                    out.comment("Es un tipo no basico, hay que evaluarlo primero");

                    out.comment("FROM");
                    out.indented(() -> {
                        element.compileAsExpr(out);
                    });

                    out.comment("TO");
                    out.mem_local(this.position);
                    out.i32_load();
                    out.i32_const(i * elements.get(i).type().size());
                    out.i32_add();

                    out.comment("SIZE");
                    out.i32_const(element.type().size() / 4);

                    out.call(ProgramOutput.COPYN);
                }
            }
        });

        /// Devolver dirección del inicio del array
        out.mem_local(this.position);
        out.i32_load();
    }
}
