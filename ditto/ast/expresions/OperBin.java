package ditto.ast.expresions;

import ditto.ast.types.Type;

public class OperBin extends Expresions{
    public enum Operators {
        SUM,
        SUBS,
        MUL,
        DIV,
        MODULO,
        EQUALS,
        NOTEQUALS,
        LESS,
        GREATER,
        LESS_EQUAL,
        GREATER_EQUAL,
        AND,
        OR,
        ACCESS,
        ACCESS_ARR
    }

    private final Operators op;
    private final Expresions left;
    private final Expresions right;

    OperBin(Operators op, Expresions left, Expresions right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public Expresions getLeft() {
        return left;
    }

    public Expresions getRight() {
        return right;
    }

    public Operators getOp() {
        return op;
    }

    /*Funciones de Expresions */
    
    public int eval(){  return 0;    }

    public Type getTipo(){  return this.left.type;    }

    public String toString() {
        return "(" + op + " " + left + " " + right + ")";
    }
}
