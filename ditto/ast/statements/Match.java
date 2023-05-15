package ditto.ast.statements;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import ditto.ast.Delta;
import ditto.ast.Context;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.BoolType;
import ditto.ast.types.IntegerType;
import ditto.errors.TypeError;

public class Match extends Statement {
    private Expr expr;
    private List<Case> cases;
    private List<Case> nonOtherwiseCases;
    private Case otherwiseCase;
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;

    public Match(Expr expr, List<Case> cases) {
        this.expr = expr;
        this.cases = cases;

        this.nonOtherwiseCases = this.cases.stream()
                .filter(c -> !c.isOtherwise).collect(Collectors.toList());

        /// Por CUP, solo puede haber un otherwise a lo sumo
        this.otherwiseCase = this.cases.stream().filter(c -> c.isOtherwise).findFirst().orElse(null);
    }

    static public class Case extends Statement {
        public final Expr expr;
        public int exprValue;
        public final List<Statement> body;
        public boolean isOtherwise = false;

        /// Otherwise case
        public Case(List<Statement> body) {
            this(null, body);
            this.isOtherwise = true;
        }

        public Case(Expr expr, List<Statement> body) {
            this.expr = expr;
            this.body = body;
        }

        @Override
        public String getAstString() {
            if (isOtherwise)
                return "otherwise";
            else
                return "case";
        }

        @Override
        public List<Object> getAstArguments() {
            return Arrays.asList(expr, body);
        }

        @Override
        public List<Node> getAstChildren() {
            List<Node> children = new ArrayList<Node>();
            if (!isOtherwise)
                children.add(expr);
            children.addAll(body);
            return children;
        }

        @Override
        public void bind(Context ctx) {
            ctx.pushScope();
            super.bind(ctx);
            if (!isOtherwise) {
                this.exprValue = this.expr.evalIntAtCompileTime();
            } else {
                this.exprValue = Integer.MAX_VALUE;
            }
            ctx.popScope();
        }

        @Override
        public void computeOffset(Delta delta) {
            delta.enterBlock();
            super.computeOffset(delta);
            delta.exitBlock();
        }

        @Override
        public String decompile() {
            if (!isOtherwise) {
                return String.format("case %s:  body ", exprValue);
            } else {
                return String.format("otherwise:  body");
            }
        }

        @Override
        public void compileAsInstruction(ProgramOutput out) {
            out.comment("INSTRUCTION: " + this.decompile());
            for (Statement s : body) {
                s.compileAsInstruction(out);
            }
        }
    }

    @Override
    public String getAstString() {
        return "match";
    }

    @Override
    public List<Object> getAstArguments() {
        return Arrays.asList(expr, cases);
    }

    @Override
    public List<Node> getAstChildren() {
        List<Node> children = new ArrayList<Node>();
        children.add(expr);
        children.addAll(cases);
        return children;
    }

    @Override
    public void typecheck() {
        super.typecheck();
        if (!expr.type().equals(BoolType.getInstance()) && !expr.type().equals(IntegerType.getInstance())) {
            throw new TypeError(String.format("Expression in match must be of type bool or int, got %s",
                    expr.type()));
        }

        for (Case c : this.nonOtherwiseCases) {
            if (!expr.type().equals(c.expr.type())) {
                throw new TypeError("Type mismatch in case");
            }
        }

        /// Ordenar los casos que no son otherwise por el valor de su expresion
        Collections.sort(nonOtherwiseCases, new Comparator<Match.Case>() {
            public int compare(Match.Case o1, Match.Case o2) {
                return o1.exprValue - o2.exprValue;
            }
        });

        /// Comprobar que no hay casos repetidos
        for (int i = 0; i < nonOtherwiseCases.size() - 1; ++i) {
            if (nonOtherwiseCases.get(i).exprValue == nonOtherwiseCases.get(i + 1).exprValue) {
                throw new TypeError(String.format("Repeated case %d in match", nonOtherwiseCases.get(i).exprValue));
            }
        }

        /// Calculamos el minimo y el maximo
        /// La razon es porque br_table del WASM empieza en 0
        /// Entonces si tenemos un match con casos entre 5 y 10, tenemos que restar 5
        // para que empiece en 0
        if (nonOtherwiseCases.size() > 0) {
            min = nonOtherwiseCases.get(0).exprValue;
            max = nonOtherwiseCases.get(nonOtherwiseCases.size() - 1).exprValue;
        }
    }

    @Override
    public String decompile() {
        String res = String.format("match %s: \n", expr.decompile());
        for (Case c : this.cases) {
            res += c.decompile();
        }
        return res;
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        out.comment("INSTRUCTION: " + this.decompile());

        /// Rango de los casos que no son otherwise
        int rango = 0;
        if (nonOtherwiseCases.size() > 0) {
            rango = max - min + 1;
        }

        /// El primer block es para poder salir del match
        /// El ultimo block es para poner br_table y la expresion a evaluar por dentro
        for (int i = 0; i < rango + 2; ++i) {
            out.block();
            out.indent();
        }

        out.comment("Bloque n + 2");
        out.comment(String.format("Cargar el valor del match %s", this.expr.decompile()));
        this.expr.compileAsExpr(out);
        out.comment("Ajustar el valor para que este en el rango");
        out.i32_const(min);
        out.i32_sub();

        out.br_table(rango);
        out.comment("Fin bloque n + 2");
        out.end();

        /// Ahora el caso min -> 0, el caso min + 1 -> 1, etc
        int j = 0; /// Indice por el que vamos en la lista de nonOtherwiseCases
        for (int i = 0; i < rango; ++i) {
            out.comment(String.format("Caso %d de br_table", i));

            Case c = nonOtherwiseCases.get(j);
            if (c.exprValue == i + min) {
                out.comment(String.format("Caso %d de match", c.exprValue));
                c.compileAsInstruction(out);
                ++j;
            }

            out.br(rango - i);
            out.end();
        }

        if (otherwiseCase != null) {
            out.comment("Caso otherwise");
            otherwiseCase.compileAsInstruction(out);
        }

        out.end();
    }
}
