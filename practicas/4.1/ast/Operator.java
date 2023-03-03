package ast;

public enum Operator {
    SUM(KindE.SUMA, "suma"), MUL(KindE.MUL, "mul");
    
    
    public final String toString;
    public final KindE kind;
    private Operator(KindE kind, String s){
        this.toString = s;
        this.kind = kind;
    }
}