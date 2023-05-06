package ditto.ast.statements;

import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import ditto.ast.Delta;
import ditto.ast.Context;
import ditto.ast.Node;
import ditto.ast.ProgramOutput;
import ditto.ast.expressions.Expr;
import ditto.ast.types.IntegerType;
import ditto.ast.types.Type;
import ditto.errors.TypeError;

public class Match extends Statement {
    private Expr expr;
    private List<Case> cases;
    private int exprValue;
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;

    public Match(Expr expr, List<Case> cases) {
        this.expr = expr;
        this.cases = cases;
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
            if(!isOtherwise){
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
        public String decompile(){
            if(!isOtherwise)    {return     String.format("case %s:  body ", exprValue);    }
            else    {   return String.format("otherwise:  body"); }
        }
        
        @Override
        public void compileAsInstruction(ProgramOutput out) {
            out.comment("INSTRUCTION: " + this.decompile());
            for(Statement s : body){
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
        Type matchingType = IntegerType.getInstance();

        if(expr.type() != matchingType){
            throw new TypeError("Type mismatch in match evaluated expression");
        }

        for (Case c : this.cases) {
            if (!c.isOtherwise && c.expr != null && !matchingType.equals(c.expr.type())) {
                throw new TypeError("Type mismatch in case");
            }
        }

        //Aprovechamos que todas las expresiones ya están procesadas para calcular el valor mínimo y máximo
        // y ordenar la lista de cases

        //Vamos a ordenar el array en base al valor de las expresiones
        Collections.sort(cases, new Comparator<Match.Case>(){
            public int compare(Match.Case o1, Match.Case o2) {
                return o1.exprValue - o2.exprValue;
            }
        });

        //Calculamos el minimo y el maximo
        if(!cases.get(0).isOtherwise){
            min = cases.get(0).exprValue;
        }
        if(!cases.get(cases.size()-1).isOtherwise){
            max = cases.get(cases.size()-1).exprValue;
        } else if(cases.size() > 1 && !cases.get(cases.size()-2).isOtherwise){
            max = cases.get(cases.size()-2).exprValue;
        }


        //Ajustamos los valores de la expresion del match y de los casos para el minimo

    }

    @Override 
    public String decompile(){
        String res = String.format("match %s: \n", expr.decompile());
        for(Case c : this.cases){
            res += c.decompile();
        }
        return res;
    }

    @Override
    public void compileAsInstruction(ProgramOutput out) {
        out.comment("INSTRUCTION: " + this.decompile());
        
        //Cojemos el maximo valor posible
        int n = this.max;

        for(int i = min; i < n + 2; ++i){
            out.block();
        }

        out.comment("n + 2 times block");
        
        //Cargamos el valor de la expresion actualizado
        if(min > 0){
            out.i32_const(exprValue);
            out.i32_const(min);
            out.i32_sub();
        } else{
            out.i32_const(exprValue);
            out.i32_const(min);
            out.i32_add();
        }

        out.br_table(n - min);
        out.end();

        for(int i = min; i <= n; ++i){
            out.indent();
            Case c = cases.get(0);
            if(c.isOtherwise){
                out.comment("caso otherwise");
                c.compileAsInstruction(out);
                cases.remove(0);
            } else {
                System.out.println(c.exprValue + " " + i);
                if(c.exprValue == i) {
                    out.comment("caso " + c.exprValue);
                    c.compileAsInstruction(out);
                    cases.remove(0);
                }
                out.comment("salta a etiqueta salir");
                out.br(n - i);
                out.comment("etiqueta " + i);
            }
            out.end();
        }
    }
}
