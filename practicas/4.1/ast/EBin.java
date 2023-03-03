package ast;

public class EBin extends E {
   private E opnd1;
   private E opnd2;
   private Operator op;

   public EBin(E opnd1, E opnd2, Operator op) {
     this.opnd1 = opnd1;
     this.opnd2 = opnd2;
     this.op = op;
   }
   public E opnd1() {return opnd1;}
   public E opnd2() {return opnd2;}  
   public KindE kind() {
      return this.op.kind;
   }
   public String toString(){
    return this.op.toString + "(" +  opnd1.toString() + ", " + opnd2.toString() + ")";
   }
  
}
